var express = require('express');
var mysql      = require('mysql');
var router = express.Router();


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

// Sign in
router.post('/join', function (req, res) {
    var user_id = req.body.user_id;
    var user_pwd = req.body.user_pwd;
    var user_name = req.body.user_name;

    console.log('id:', user_id);
    console.log('pw:', user_pwd);
    console.log('name:', user_name);

    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO user_info (user_id, user_pwd, user_name, subs_num, bookmark_num, memo_num) VALUES (?, ?, ?, ?, ?, ?)';
    var params = [user_id, user_pwd, user_name, 0, 0, 0];

    // sql 문의 ?는 두번째 매개변수로 넘겨진 params의 값으로 치환된다.
    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = '회원가입에 성공했습니다.';

            console.log(message);
        }

        res.json({
            'code': resultCode,
            'message': message,
        });
    });
});


// Login
router.post('/login', function (req, res) {
    console.log(req.body);
    var user_id = req.body.user_id;
    var user_pwd = req.body.user_pwd;
    var sql = 'select * from user_info where user_id = ?';

    connection.query(sql, user_id, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';
        var nickname;
        var subs_num;
        var bookmark_num;
        var memo_num;

        if (err) {
            console.log(err);
        } else {
            if (result.length === 0) {
                resultCode = 204;
                message = '존재하지 않는 계정입니다!';
            } else if (user_pwd !== result[0].user_pwd) {
                resultCode = 204;
                message = '비밀번호가 틀렸습니다!';
            } else {
                resultCode = 200;
                message = "로그인 성공! " + result[0].user_name + "님 환영합니다.";
                nickname = result[0].user_name;
                subs_num = result[0].subs_num;
                bookmark_num = result[0].bookmark_num;
                memo_num = result[0].memo_num;
            }
        }

        res.json({
            'code': resultCode,
            'message': message,
            'name' : nickname,
            'subs_num' : subs_num,
            'bookmark_num' : bookmark_num,
            'memo_num' : memo_num
        });
    })
});


module.exports = router;
