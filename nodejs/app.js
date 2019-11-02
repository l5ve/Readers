var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');

var app = express();

var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : "http://ec2-52-78-23-232.ap-northeast-2.compute.amazonaws.com/",
  user     : "root",
  password : "readers7",
  port     : 3306,
  database : "readers"
});

connection.connect(); // mysql 연결
connection.query('SELECT * from user_info', function(err, rows, fields) {
  if (!err)
    console.log('The solution is: ', rows);
  else
    console.log('Error while performing Query.', err);
});

connection.end();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// port setup
app.set('port', process.env.PORT || 9000);

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;

var server = app.listen(app.get('port'), function() {
console.log('Express server listening on port ' + server.address().port);
});

// Sign in
app.post('/user/join', function (req, res) {
    var user_id = req.body.user_id;
    var user_pwd = req.body.user_pwd;
    var user_name = req.body.user_name;

    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO user_info (user_id, user_pwd, user_name, subs_num, bookmark_num, subs_num) VALUES (?, ?, ?)';
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
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});

// Login
app.post('/user/login', function (req, res) {
    console.log(req.body);
    var user_id = req.body.user_id;
    var user_pwd = req.body.user_pwd;
    var sql = 'select * from Users where user_id = ?';

    connection.query(sql, user_id, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

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
                message = '로그인 성공! ' + result[0].user_name + '님 환영합니다!';
            }
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    })
});
