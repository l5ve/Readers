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
    var sql = 'INSERT INTO user_info (user_id, user_pwd, user_name) VALUES (?, ?, ?)';
    var params = [user_id, user_pwd, user_name];

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
            }
        }

        res.json({
            'code': resultCode,
            'message': message,
            'name' : nickname
        });
    })
});

router.post('/num', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var params = [user_id, user_id, user_id, user_id];
  var sql = 'select user_id, (select count(toon_id) from subscribe where user_id = ?) as subs_num, (select count(toon_id) from bookmark where user_id = ?) as bookmark_num, (select count(toon_id) from memo where user_id = ?) as memo_num from user_info where user_id = ?';

  connection.query(sql, params, function(err, rows){
    if(err) return res.sendStatus(400);
    res.status(200).json(rows);
  });
});

router.post('/genre', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var genre_name = req.body.genre_name;
  var params = [user_id, genre_name];
  var sql = 'insert into user_genre (user_id, genre_name) values (?, ?)';

  connection.query(sql, params, function(err, rows){
    if(err) return res.sendStatus(400);
    res.status(200).json(rows);
  });
});

module.exports = router;
