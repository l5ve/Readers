// ***** MySQL 데이터베이스에 연결하기 위한 기본 정보를 가지고 커넥션 풀을 생성하는 connect 함수와
// ***** 풀을 반환하는 get 함수가 선언되어 있는 파일

var mysql = require('mysql');

var pool;

exports.connect = function(done){
  pool = mysql.createPool({
    connectionLimit: 100,
    host     : "52.78.23.232",
    user     : "root",
    password : "readers7",
    port     : 3306,
    database : "readers"
  });
}

exports.get = function(){
  return pool;
}
