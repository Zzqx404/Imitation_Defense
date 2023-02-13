#coding=utf-8
import json
from flask import Flask, Response, make_response, jsonify

#引用数据库启动文件
from exts_user import db
#引用数据库配置文件
import config_user
#引用数据库
from model_user import User

app = Flask(__name__)
app.config.from_object(config_user)
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
@app.route("/getuser/<userid>")
def getMsg(userid):
    user = User.query.get(userid)
    # return jsonify(user1.to_json())  这个写法会导致把数据传到浏览器时按字典序重新排序
    json_user = user.to_json()
    # 手动按原输出顺序排好序
    sort_user = {}
    sort_user.setdefault('id', json_user['id'])
    sort_user.setdefault('username', json_user['username'])
    sort_user.setdefault('address', json_user['address'])
    sort_user.setdefault('language', 'Python')
    return Response(json.dumps(sort_user, ensure_ascii=False), mimetype='application/json')


if __name__ == '__main__':

    # 更改端口
    app.run(host="0.0.0.0", port=8091)

