var express = require('express');
var router = express.Router();
var db = require('../db');
// 생략
db.get().query(sql, phone, function(err, rows){
  // 생략
});
//생략

var connection = mysql.createConnection({
  host     : "52.78.23.232",
  user     : "root",
  password : "readers7",
  port     : 3306,
  database : "readers"
});

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.post('/memo', function (req, res) {
    console.log(req.body);
    var user_id = req.body.user_id;
    var sql = 'select * from memo where user_id = ?';

    connection.query(sql, user_id, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';
        var user_id;
        var content;
        var memo_date;

        if (err) {
            console.log(err);
        } else {
            if (result.length === 0) {
                resultCode = 204;
                message = '존재하지 않는 계정입니다!';
            }
            else {
                resultCode = 200;
                message = "success";
                user_id = result.user_id;
                content = result.content;
                memo_date = result.memo_date;
            }
        }

        res.json({
            'code': resultCode,
            'message': message,
            'user_id' : user_id,
            'content' :content,
            'memo_date' :memo_date
        });
    })
});


module.exports = router;
