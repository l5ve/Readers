var express = require('express');
var router = express.Router();
var mysql = require('mysql');

var connection = mysql.createConnection({
  host     : "52.78.23.232",
  user     : "root",
  password : "readers7",
  port     : 3306,
  database : "readers"
});

router.post('/add', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var toon_id = req.body.toon_id;
  var params = [user_id, toon_id];

  var exist_subs_sql = 'select * from subscribe where user_id = ? and toon_id = ?';
  var ins_subs_sql = 'insert into subscribe (user_id, toon_id) values (?, ?)';

  var genre_name;
  var genre_params;
  var sel_tgenre_sql = 'select genre_name from toon_genre where toon_id = ?';
  var exist_ugenre_sql = 'select * from user_genre where user_id = ? and genre_name = ?';
  var ins_genre_sql = 'insert into user_genre (user_id, genre_name, genre_count) values (?, ?, 10);'
  var update_genre_sql = 'update user_genre set genre_count = genre_count + 10 where user_id = ? and genre_name = ?';

  connection.query(exist_subs_sql, params, function(err, rows){
    if(err) {
      console.log('error!');
      return res.sendStatus(400);
    }

    if (rows.length === 0){ // 구독 추가
      connection.query(ins_subs_sql, params, function(err, rows){
        if(err) return res.sendStatus(400);

        connection.query(sel_tgenre_sql, toon_id, function(err, rows){
          if(err) return res.sendStatus(400);

          // async function printAll() {
          //   for(var i = 0; i < rows.length; i++ ) { // for 안에서 비동기 함수가 동작할 것이다.
          //     await pushAsync(i);  //promise 를 리턴해야 await 로 사용 가능 하다.
          //   }
          // }
          //
          // function pushAsync(i){
          //   return new Promise((resolve) => {
          //     setTimeout(() => {
          //
          //       genre_name = rows[i].genre_name;
          //       console.log(genre_name);
          //       genre_params = [user_id, genre_name];
          //       console.log(genre_params);
          //
          //       connection.query(exist_ugenre_sql, genre_params, function(err, rows){
          //         if(err) return res.sendStatus(400);
          //
          //         console.log("exist_ugenre : " + genre_params, rows.length);
          //         if (rows.length == 0){
          //           connection.query(ins_genre_sql, genre_params, function(err, rows){
          //             console.log("insert genre : " + genre_name);
          //             if(err) return res.sendStatus(400);
          //           });
          //         }
          //
          //         else{
          //           console.log("have to update");
          //           connection.query(update_genre_sql, genre_params, function(err, rows){
          //             console.log("update genre : " + genre_name);
          //             if(err) return res.sendStatus(400);
          //           });
          //         }
          //       });
          //
          //       resolve("resolve");
          //     }, 1000);
          //   });
          // }

          return res.sendStatus(200);
          // for (var i = 0; i < rows.length; i++){
          //
          //   genre_name = rows[i].genre_name;
          //   console.log(genre_name);
          //   genre_params = [user_id, genre_name];
          //   console.log(genre_params);
          //
          //   connection.query(exist_ugenre_sql, genre_params, function(err, rows){
          //     if(err) return res.sendStatus(400);
          //
          //     console.log("exist_ugenre : " + genre_params, rows.length);
          //     if (rows.length == 0){
          //       connection.query(ins_genre_sql, genre_params, function(err, rows){
          //         console.log("insert genre : " + genre_name);
          //         if(err) return res.sendStatus(400);
          //         else return res.sendStatus(200);
          //       });
          //     }
          //
          //     else{
          //       console.log("have to update");
          //       // connection.query(update_genre_sql, genre_params, function(err, rows){
          //       //   console.log("update genre : " + genre_name);
          //       //   if(err) return res.sendStatus(400);
          //       //   else return res.sendStatus(200);
          //       // });
          //     }
          //   });
          // }
        });
      });
    }

    else{
      res.send('duplicate subscribe');
    }
  });
});

router.post('/delete', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var toon_id = req.body.toon_id;
  var params = [user_id, toon_id];

  var exist_subs_sql = 'select * from subscribe where user_id = ? and toon_id = ?';
  var del_subs_sql = 'delete from subscribe where user_id = ? and toon_id = ?';

  connection.query(exist_subs_sql, params, function (err, rows) {
    if(err) return res.sendStatus(400);

    if (rows.length !== 0){
      connection.query(del_subs_sql, params, function(err, rows){
        if(err) return res.sendStatus(400);
        else return res.sendStatus(200);
      });
    }

    else res.send('not exist');
  });
});

router.post('/list', function(req, res){
  console.log(req.body);
  var user_id = req.body.user_id;
  var order_by = req.body.order_by; // name, update, site

  var name_sql = 'select i.toon_id, i.toon_site, i.toon_name, i.wrt_name, i.toon_thumb_url, max(epi_date) as last_date from subscribe as s, toon_info as i, epi_info as e where s.user_id = ? and s.toon_id = i.toon_id and i.toon_id = e.toon_id group by i.toon_id order by toon_name';
  var update_sql = 'select i.toon_id, i.toon_site, i.toon_name, i.wrt_name, i.toon_thumb_url, max(epi_date) as last_date from subscribe as s, toon_info as i, epi_info as e where s.user_id = ? and s.toon_id = i.toon_id and i.toon_id = e.toon_id group by i.toon_id order by last_date desc, toon_name';
  var site_sql = 'select i.toon_id, i.toon_site, i.toon_name, i.wrt_name, i.toon_thumb_url, max(epi_date) as last_date from subscribe as s, toon_info as i, epi_info as e where s.user_id = ? and s.toon_id = i.toon_id and i.toon_id = e.toon_id group by i.toon_id order by toon_site, toon_name';

  if (order_by == 'name'){
    connection.query(name_sql, user_id, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }

  else if (order_by == 'update'){
    connection.query(update_sql, user_id, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }

  else {
    connection.query(site_sql, user_id, function(err, rows){
      if(err) return res.sendStatus(400);

      console.log("rows : " + JSON.stringify(rows));
      res.status(200).json(rows);
    });
  }
});

module.exports = router;
