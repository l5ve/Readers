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

router.post('/genrecount', function(req, res){
  var user_id = req.body.user_id;
  var params = [user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id];
  var sql = `
  select g.user_id, IFNULL(recC_num, 0)+IFNULL(recD_num, 0) as rec_num, g.genre_name
  from (select user_id, count(genre_name)*100 as recC_num, genre_name
  from user_genre
  where user_id = ? group by genre_name) as g
  left join
  (select e.user_id, recA_num+recB_num as recD_num, e.genre_name
  from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
  from (select user_id, count(g.toon_id) as bookmark_num, genre_name
  from bookmark as bm, toon_genre as g
  where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
  left join
  (select user_id, count(g.toon_id) as memo_num, genre_name
  from memo as m, toon_genre as g
  where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
  on c.genre_name = d.genre_name
  union
  select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
  from (select user_id, count(g.toon_id) as bookmark_num, genre_name
  from bookmark as bm, toon_genre as g
  where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
  right join
  (select user_id, count(g.toon_id) as memo_num, genre_name
  from memo as m, toon_genre as g
  where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
  on c.genre_name = d.genre_name) as e
  left join
  (select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
  from (select user_id, count(g.toon_id)*10 as block_num, genre_name
  from user_block as b, toon_genre as g
  where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
  left join
  (select user_id, count(g.toon_id)*10 as subs_num, genre_name
  from subscribe as s, toon_genre as g
  where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
  on a.genre_name = b.genre_name
  union
  select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
  from (select user_id, count(g.toon_id)*10 as block_num, genre_name
  from user_block as b, toon_genre as g
  where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
  right join
  (select user_id, count(g.toon_id)*10 as subs_num, genre_name
  from subscribe as s, toon_genre as g
  where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
  on a.genre_name = b.genre_name) as f
  on e.genre_name = f.genre_name
  UNION
  select f.user_id, recA_num+recB_num as recD_num, f.genre_name
  from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
  from (select user_id, count(g.toon_id) as bookmark_num, genre_name
  from bookmark as bm, toon_genre as g
  where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
  left join
  (select user_id, count(g.toon_id) as memo_num, genre_name
  from memo as m, toon_genre as g
  where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
  on c.genre_name = d.genre_name
  union
  select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
  from (select user_id, count(g.toon_id) as bookmark_num, genre_name
  from bookmark as bm, toon_genre as g
  where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
  right join
  (select user_id, count(g.toon_id) as memo_num, genre_name
  from memo as m, toon_genre as g
  where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
  on c.genre_name = d.genre_name) as e
  left join
  (select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
  from (select user_id, count(g.toon_id)*10 as block_num, genre_name
  from user_block as b, toon_genre as g
  where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
  left join
  (select user_id, count(g.toon_id)*10 as subs_num, genre_name
  from subscribe as s, toon_genre as g
  where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
  on a.genre_name = b.genre_name
  union
  select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
  from (select user_id, count(g.toon_id)*10 as block_num, genre_name
  from user_block as b, toon_genre as g
  where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
  right join
  (select user_id, count(g.toon_id)*10 as subs_num, genre_name
  from subscribe as s, toon_genre as g
  where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
  on a.genre_name = b.genre_name) as f
  on e.genre_name = f.genre_name) as h
  on g.genre_name = h.genre_name
  UNION
  select h.user_id, IFNULL(recC_num, 0)+IFNULL(recD_num, 0) as rec_num, h.genre_name
  from (select user_id, count(genre_name)*100 as recC_num, genre_name
  from user_genre
  where user_id = ? group by genre_name) as g
  right join
  (select e.user_id, recA_num+recB_num as recD_num, e.genre_name
  from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
  from (select user_id, count(g.toon_id) as bookmark_num, genre_name
  from bookmark as bm, toon_genre as g
  where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
  left join
  (select user_id, count(g.toon_id) as memo_num, genre_name
  from memo as m, toon_genre as g
  where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
  on c.genre_name = d.genre_name
  union
  select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
  from (select user_id, count(g.toon_id) as bookmark_num, genre_name
  from bookmark as bm, toon_genre as g
  where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
  right join
  (select user_id, count(g.toon_id) as memo_num, genre_name
  from memo as m, toon_genre as g
  where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
  on c.genre_name = d.genre_name) as e
  left join
  (select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
  from (select user_id, count(g.toon_id)*10 as block_num, genre_name
  from user_block as b, toon_genre as g
  where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
  left join
  (select user_id, count(g.toon_id)*10 as subs_num, genre_name
  from subscribe as s, toon_genre as g
  where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
  on a.genre_name = b.genre_name
  union
  select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
  from (select user_id, count(g.toon_id)*10 as block_num, genre_name
  from user_block as b, toon_genre as g
  where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
  right join
  (select user_id, count(g.toon_id)*10 as subs_num, genre_name
  from subscribe as s, toon_genre as g
  where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
  on a.genre_name = b.genre_name) as f
  on e.genre_name = f.genre_name
  UNION
  select f.user_id, recA_num+recB_num as recD_num, f.genre_name
  from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
  from (select user_id, count(g.toon_id) as bookmark_num, genre_name
  from bookmark as bm, toon_genre as g
  where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
  left join
  (select user_id, count(g.toon_id) as memo_num, genre_name
  from memo as m, toon_genre as g
  where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
  on c.genre_name = d.genre_name
  union
  select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
  from (select user_id, count(g.toon_id) as bookmark_num, genre_name
  from bookmark as bm, toon_genre as g
  where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
  right join
  (select user_id, count(g.toon_id) as memo_num, genre_name
  from memo as m, toon_genre as g
  where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
  on c.genre_name = d.genre_name) as e
  left join
  (select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
  from (select user_id, count(g.toon_id)*10 as block_num, genre_name
  from user_block as b, toon_genre as g
  where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
  left join
  (select user_id, count(g.toon_id)*10 as subs_num, genre_name
  from subscribe as s, toon_genre as g
  where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
  on a.genre_name = b.genre_name
  union
  select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
  from (select user_id, count(g.toon_id)*10 as block_num, genre_name
  from user_block as b, toon_genre as g
  where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
  right join
  (select user_id, count(g.toon_id)*10 as subs_num, genre_name
  from subscribe as s, toon_genre as g
  where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
  on a.genre_name = b.genre_name) as f
  on e.genre_name = f.genre_name) as h
  on g.genre_name = h.genre_name
  order by rec_num desc;
  `;

  connection.query(sql, params, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
});

router.post('/recommend', function(req, res){
  var user_id = req.body.user_id;
  var genre_name = req.body.genre_name;
  var params = [user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id, user_id,
    user_id, user_id, user_id, user_id];

  var sql = `
  (select i.toon_id, i.toon_thumb_url, i.toon_site, i.toon_name, i.wrt_name, i.toon_desc, g.genre_name
from toon_info as i, toon_genre as g
where i.toon_id = g.toon_id and g.genre_name = (select x.genre_name
from (select g.user_id, IFNULL(recC_num, 0)+IFNULL(recD_num, 0) as rec_num, g.genre_name
from (select user_id, count(genre_name)*100 as recC_num, genre_name
from user_genre
where user_id = ? group by genre_name) as g
left join
(select e.user_id, recA_num+recB_num as recD_num, e.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name
UNION
select f.user_id, recA_num+recB_num as recD_num, f.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name) as h
on g.genre_name = h.genre_name
UNION
select h.user_id, IFNULL(recC_num, 0)+IFNULL(recD_num, 0) as rec_num, h.genre_name
from (select user_id, count(genre_name)*100 as recC_num, genre_name
from user_genre
where user_id = ? group by genre_name) as g
right join
(select e.user_id, recA_num+recB_num as recD_num, e.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name
UNION
select f.user_id, recA_num+recB_num as recD_num, f.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name) as h
on g.genre_name = h.genre_name
order by rec_num desc
LIMIT 0, 1) as x)
and i.toon_id not in (select toon_id from subscribe where user_id = ?)
and i.toon_id not in (select toon_id from memo where user_id = ?)
and i.toon_id not in (select toon_id from bookmark where user_id = ?)
and i.toon_id not in (select toon_id from user_block where user_id = ?)
ORDER BY RAND() LIMIT 1)
UNION
(select i.toon_id, i.toon_thumb_url, i.toon_site, i.toon_name, i.wrt_name, i.toon_desc, g.genre_name
from toon_info as i, toon_genre as g
where i.toon_id = g.toon_id and g.genre_name = (select x.genre_name
from (select g.user_id, IFNULL(recC_num, 0)+IFNULL(recD_num, 0) as rec_num, g.genre_name
from (select user_id, count(genre_name)*100 as recC_num, genre_name
from user_genre
where user_id = ? group by genre_name) as g
left join
(select e.user_id, recA_num+recB_num as recD_num, e.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name
UNION
select f.user_id, recA_num+recB_num as recD_num, f.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name) as h
on g.genre_name = h.genre_name
UNION
select h.user_id, IFNULL(recC_num, 0)+IFNULL(recD_num, 0) as rec_num, h.genre_name
from (select user_id, count(genre_name)*100 as recC_num, genre_name
from user_genre
where user_id = ? group by genre_name) as g
right join
(select e.user_id, recA_num+recB_num as recD_num, e.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name
UNION
select f.user_id, recA_num+recB_num as recD_num, f.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name) as h
on g.genre_name = h.genre_name
order by rec_num desc
LIMIT 1, 1) as x)
and i.toon_id not in (select toon_id from subscribe where user_id = ?)
and i.toon_id not in (select toon_id from memo where user_id = ?)
and i.toon_id not in (select toon_id from bookmark where user_id = ?)
and i.toon_id not in (select toon_id from user_block where user_id = ?)
ORDER BY RAND() LIMIT 1)
UNION
(select i.toon_id, i.toon_thumb_url, i.toon_site, i.toon_name, i.wrt_name, i.toon_desc, g.genre_name
from toon_info as i, toon_genre as g
where i.toon_id = g.toon_id and g.genre_name = (select x.genre_name
from (select g.user_id, IFNULL(recC_num, 0)+IFNULL(recD_num, 0) as rec_num, g.genre_name
from (select user_id, count(genre_name)*100 as recC_num, genre_name
from user_genre
where user_id = ? group by genre_name) as g
left join
(select e.user_id, recA_num+recB_num as recD_num, e.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name
UNION
select f.user_id, recA_num+recB_num as recD_num, f.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name) as h
on g.genre_name = h.genre_name
UNION
select h.user_id, IFNULL(recC_num, 0)+IFNULL(recD_num, 0) as rec_num, h.genre_name
from (select user_id, count(genre_name)*100 as recC_num, genre_name
from user_genre
where user_id = ? group by genre_name) as g
right join
(select e.user_id, recA_num+recB_num as recD_num, e.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name
UNION
select f.user_id, recA_num+recB_num as recD_num, f.genre_name
from (select c.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, c.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
left join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name
union
select d.user_id, IFNULL(bookmark_num, 0)+IFNULL(memo_num, 0) as recB_num, d.genre_name
from (select user_id, count(g.toon_id) as bookmark_num, genre_name
from bookmark as bm, toon_genre as g
where bm.toon_id = g.toon_id and user_id = ? group by genre_name) as c
right join
(select user_id, count(g.toon_id) as memo_num, genre_name
from memo as m, toon_genre as g
where m.toon_id = g.toon_id and user_id = ? group by genre_name) as d
on c.genre_name = d.genre_name) as e
left join
(select a.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, a.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
left join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name
union
select b.user_id, IFNULL(subs_num, 0)-IFNULL(block_num, 0) as recA_num, b.genre_name
from (select user_id, count(g.toon_id)*10 as block_num, genre_name
from user_block as b, toon_genre as g
where b.toon_id = g.toon_id and user_id = ? group by genre_name) as a
right join
(select user_id, count(g.toon_id)*10 as subs_num, genre_name
from subscribe as s, toon_genre as g
where s.toon_id = g.toon_id and user_id = ? group by genre_name) as b
on a.genre_name = b.genre_name) as f
on e.genre_name = f.genre_name) as h
on g.genre_name = h.genre_name
order by rec_num desc
LIMIT 2, 1) as x)
and i.toon_id not in (select toon_id from subscribe where user_id = ?)
and i.toon_id not in (select toon_id from memo where user_id = ?)
and i.toon_id not in (select toon_id from bookmark where user_id = ?)
and i.toon_id not in (select toon_id from user_block where user_id = ?)
ORDER BY RAND() LIMIT 1);
  `;

  connection.query(sql, params, function(err, rows){
    if(err) return res.sendStatus(400);

    console.log("rows : " + JSON.stringify(rows));
    res.status(200).json(rows);
  });
})

module.exports = router;
