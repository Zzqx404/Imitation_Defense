// 导入mysql
const mysql = require('mysql')
// 导入express
const express = require('express')
// 创建服务器
let app=express()
var http =require('http');
const axios = require("axios");
let both

// 通过createPool方法连接服务器
const db = mysql.createPool({
    host: '127.0.0.1', // 表示连接某个服务器上的mysql数据库
    user: 'root', // 数据库的用户名 （默认为root）
    password: 'root', // 数据库的密码
    database: 'test',// 创建的本地数据库名称
})

// 通过nodejs获取数据库中的数据  并返回给客户端------------------
app.get('/getorder/:id', (req, res) => {
    const id = req.params.id
    db.query('select * from tb_order where id = ?', id, (err, orderpart) => {
        if (err) return console.log(err.message); // 连接失败
        // 否则获取成功，写函数查询user将二者结合返回给客户端res.send
        orderpart = JSON.stringify(orderpart)
        orderpart = JSON.parse(orderpart)//去掉多余的一段字符串
        orderpart = orderpart[0]//[0]去掉中括号

        let url = 'http://localhost:8050/os/toUs/' + orderpart.user_id;
        axios.get(url)
            .then(function (response) {
                // 处理成功情况
                console.log(response.data);//user部分
                console.log(orderpart)//order部分
                // 接下来合并并且最终的输出
                // response.data["new"] = str
                orderpart.user=response.data
                both=orderpart//合并赋值给both
                console.log(both);//test
                res.json(both)
                // res.json(response.data)
                // res.send('Hello World!')
            })
            .catch(function (error) {
                // 处理错误情况
                console.log(error);
            })
            .then(function () {
                // 总是会执行
                console.log('111111111122')//测试，无用
            });
    })
})

app.get('/health', (req, res) => {
    res.json({"status": "UP"});
})

// 启动服务器
app.listen(8010, () => {
    console.log('jsorder running on localhost:8010');
})