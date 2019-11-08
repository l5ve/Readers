var express = require('express');
var router = express.Router();
var db = require('../db');
// 생략
db.get().query(sql, params, function(err, rows){
  // 생략
});
//생략

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
