var createError = require('http-errors');
var path = require('path');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var express = require('express');
var app = express();

var schedule = require('node-schedule');
var spawn = require('child_process').spawn;

var job = schedule.scheduleJob('0 30 12 * * *', function(){
  var process1 = spawn('python3', ["./crawling/naver_end.py"]);
  var process2 = spawn('python3', ["./crawling/naver_in_series.py"]);
  var process3 = spawn('python3', ["./crawling/naver_episode.py"]);
  console.log(`I'm crawling now`);
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

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
app.use('/bookmark', require('./routes/bookmark'));
app.use('/memo', require('./routes/memo'));
app.use('/subscribe', require('./routes/subscribe'));
app.use('/toon', require('./routes/toon'));
app.use('/block', require('./routes/block'));
app.use('/recommend', require('./routes/recommend'));

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
