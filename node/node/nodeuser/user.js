// 导入mysql
const mysql = require('mysql')
// 导入express
const express = require('express')
const http = require("http");
// const {response} = require("express");
// 创建服务器
const app = express()
// 通过createPool方法连接服务器

const db = mysql.createPool({
    host: '127.0.0.1', // 表示连接某个服务器上的mysql数据库
    user: 'root', // 数据库的用户名 （默认为root）
    password: 'root', // 数据库的密码
    database: 'test',// 创建的本地数据库名称
})

// 通过nodejs获取数据库中的数据  并返回给客户端------------------
app.get('/getuser/:id', (req, res) => {
    // 通过db.query方法来执行mysql  测试是否连接成功
    // 查询语句 data 得到的是一个数组，  增删改得到的是受影响的行数
    const id1=req.params.id
    db.query("select * from tb_user where id = ?",id1, (err, data) => {
        if (err) return console.log(err.message); // 连接失败
        // 否则获取成功，将结果返回给客户端res.send
        data=JSON.stringify(data)
        data = JSON.parse(data)//去掉多余的一段字符串
        // data=data.replace("[","");
        // data=data.replace("]","");
        data=data[0]//[]去掉中括号
        data["language"] = "nodeJS"
        res.send(data)
        console.log(data)
    })})

app.get('/health', (req, res) => {
    res.json({"status": "UP"});
})


// 启动服务器
app.listen(8011, () => {
    console.log('jsuser running on localhost:8011/');
})