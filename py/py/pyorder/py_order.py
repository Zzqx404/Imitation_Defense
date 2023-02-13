#coding=utf-8
import json
from flask import Flask, Response, make_response, jsonify
import requests

#引用数据库启动文件
from exts_order import db
#引用数据库配置文件
import config_order
#引用数据库
from model_order import Order

app = Flask(__name__)
app.config.from_object(config_order)
db.init_app(app)
# 修改输出信息的编码
app.config['JSON_AS_ASCII'] = False
app.config['JSONIFY_MIMETYPE'] = "application/json;charset=utf-8"

# 返回健康状态
@app.route("/health")
def health():
    result = {'status': 'UP'}
    return Response(json.dumps(result), mimetype='application/json')

# 查询用户信息
@app.route("/getorder/<orderid>")
def getorder1(orderid):
    # result = {'status': 'UP'}
    # return Response(json.dumps(result), mimetype='application/json')
    order = Order.query.get(orderid)
    # url = "http://localhost:8091/getuser/" + str(order.user_Id)
    # url = "http://localhost:8061/getuser/" + str(order.user_Id)
    # url = "http://localhost:9100/user/getuser/" + str(order.user_Id)

    # 定向访问 POS
    url = "http://localhost:8050/os/toUs/"+ str(order.user_Id)

    user = requests.get(url)
    json_user = json.loads(user.text) # 变为字典
    json_order = order.to_json()  # 将SQLAlchemy对象变为为字典
    json_order.setdefault('user', json_user)
    # return jsonify(json_order) # 这个写法会导致把数据传到浏览器时按字典序重新排序
    # 手动按原输出顺序排好序
    sort_order = {}
    sort_order.setdefault('id', json_order['id'])
    sort_order.setdefault('price', json_order['price'])
    sort_order.setdefault('name', json_order['name'])
    sort_order.setdefault('num', json_order['num'])
    sort_order.setdefault('userId', json_order['user_Id'])
    sort_order.setdefault('user', json_user) # 添加user键值对
    return Response(json.dumps(sort_order, ensure_ascii=False), mimetype='application/json')

if __name__ == '__main__':
    # 更改端口（默认5000）
    app.run(host="0.0.0.0", port=8090)
