
-- 발행주식수 업데이트 
UPDATE `실적_분기` A LEFT OUTER JOIN `종목_상세` B ON A.종목코드 = B.종목코드
 SET A.발행주식수 = B.발행주식수

UPDATE `실적_연간` A LEFT OUTER JOIN `종목_상세` B ON A.종목코드 = B.종목코드
 SET A.발행주식수 = B.발행주식수;

-- 시총 업데이트 
UPDATE `실적_분기` A LEFT OUTER JOIN `거래_일` B ON A.종목코드 = B.종목코드 AND B.일자 = '20180525'
 SET A.시총 = ROUND( B.종가 * A.발행주식수/100000000 )
; 

UPDATE `실적_연간` A LEFT OUTER JOIN `거래_일` B ON A.종목코드 = B.종목코드 AND B.일자 = '20180525'
 SET A.시총 = ROUND( B.종가 * A.발행주식수/100000000 )
; 

 
SELECT 종목코드, 종목명, 시총, 순이익_2017, 순이익_2016, 순이익_2015, 순이익_2014
       , ROUND(시총/순이익_2017, 2) PER_2017
       , ROUND(시총/순이익_2016, 2) PER_2016
       , ROUND(시총/순이익_2015, 2) PER_2015
       , ROUND(시총/순이익_2014, 2) PER_2014
  FROM 
	(
	SELECT 종목코드, 종목명, MAX(시총) 시총, MAX(`발행주식수`) 발행주식수, 
	       SUM( CASE WHEN '2017' = 실적연도 THEN `당기순이익` ELSE 0 END ) 순이익_2017, 
	       SUM( CASE WHEN '2016' = 실적연도 THEN `당기순이익` ELSE 0 END ) 순이익_2016, 
	       SUM( CASE WHEN '2015' = 실적연도 THEN `당기순이익` ELSE 0 END ) 순이익_2015, 
	       SUM( CASE WHEN '2014' = 실적연도 THEN `당기순이익` ELSE 0 END ) 순이익_2014, 
	       SUM( CASE WHEN '2013' = 실적연도 THEN `당기순이익` ELSE 0 END ) 순이익_2013, 
	       SUM( CASE WHEN '2012' = 실적연도 THEN `당기순이익` ELSE 0 END ) 순이익_2012, 
	       SUM( CASE WHEN '2011' = 실적연도 THEN `당기순이익` ELSE 0 END ) 순이익_2011, 
	       SUM( CASE WHEN '2010' = 실적연도 THEN `당기순이익` ELSE 0 END ) 순이익_2010 
	  FROM `실적_연간` A
	 GROUP BY 종목코드, 종목명
	HAVING
	       SUM( CASE WHEN '2017' = 실적연도 THEN `당기순이익` ELSE 0 END )
	       >
	       SUM( CASE WHEN '2016' = 실적연도 THEN `당기순이익` ELSE 0 END )
	   AND 
	       SUM( CASE WHEN '2016' = 실적연도 THEN `당기순이익` ELSE 0 END )
	       >
	       SUM( CASE WHEN '2015' = 실적연도 THEN `당기순이익` ELSE 0 END )
	) O -- 순이익이 전전년 대비 증가한 종목.
 WHERE 1=1
 --  AND (순이익_2017/순이익_2016) > 1.1 -- 10% 이상 순이익 증가(전년도 대비)
 --  AND (순이익_2016/순이익_2015) > 1.1 -- 10% 이상 순이익 증가(전전년도 대비)
 --  AND (순이익_2015/순이익_2014) > 1.1 -- 10% 이상 순이익 증가(전전전년도 대비)
   AND 11 > 시총/순이익_2017 -- PER가 11 미만 종목.



-- 삼성전자 
SELECT 종목코드, MAX(종가) /MIN(종가)
  FROM `거래_일`
 WHERE 일자 BETWEEN '20160101' AND '20180401' 
 GROUP BY 종목코드
 HAVING MAX(종가) / MIN(종가) > 2.5


SELECT MIN(일자)
  FROM `거래_일`
 WHERE 종목코드  = '005930'
   AND 일자 BETWEEN '20160101' AND '20180401' 
   
SELECT * FROM `종목_상세`


SELECT 
       C.종가 / ((연간4*100000000) / B.발행주식수) PER,
       A.*,
       B.*,
       C.*
  FROM 재무 A 
       LEFT OUTER JOIN 종목_상세 B ON A.종목코드 = B.종목코드
       LEFT OUTER JOIN 거래_일 C ON C.종목코드 = A.종목코드 AND C.일자 = '20180525'
 WHERE 1=1
   AND 재무구분 = '당기순이익'
   -- AND 연간1 < 연간2 AND 연간2 < 연간3 AND 연간3 < 연간4
   AND  연간2 < 연간3 AND 연간3 < 연간4
   AND C.종가 / ((연간4*100000000) / B.발행주식수) < 12 -- PER   
   AND A.종목코드 IN (  SELECT 종목코드 FROM 기업개요  WHERE 개요 LIKE '%반도체%'  )
 ORDER BY C.종가 / ((연간4*100000000) / B.발행주식수)  ASC




SELECT * FROM 거래_일 C 
WHERE  C.일자 = '20180525'
  AND 등락률 < -15
 ORDER BY 등락률 DESC 

SELECT *
  FROM 재무 A LEFT OUTER JOIN 종목_상세 B ON A.종목코드 = B.종목코드
 WHERE 1=1
   AND 재무구분 = '당기순이익'


UPDATE `종목_상세` A LEFT OUTER JOIN `거래_일` B ON A.종목코드 = B.종목코드 AND B.일자 = '20180525'
 SET A.A종가 = B.종가
; 


UPDATE `재무` A LEFT OUTER JOIN `종목_상세` B ON A.종목코드 = B.종목코드
 SET A.발행주식수 = B.발행주식수;


SELECT * 
  FROM (
	SELECT 종목코드, 종목명 , 
	       COUNT(종가) SCOUNT, MAX(종가) SMAX, MIN(종가) SMIN, SUM(종가) SSUM, AVG(종가) SAVG, STDDEV(종가) SSTD, VARIANCE(종가) SVAR
	       ,COUNT(외인_순매수) ICOUNT, MAX(외인_순매수) IMAX, MIN(외인_순매수) IMIN, SUM(외인_순매수) ISUM, AVG(외인_순매수) IAVG, STDDEV(외인_순매수) ISTD, VARIANCE(외인_순매수) IVAR
	       ,COUNT(기관_순매수) GCOUNT, MAX(기관_순매수) GMAX, MIN(기관_순매수) GMIN, SUM(기관_순매수) GSUM, AVG(기관_순매수) GAVG, STDDEV(기관_순매수) GSTD, VARIANCE(기관_순매수) GVAR
	  FROM 거래_일 C 
	 WHERE  C.일자 BETWEEN  '20160525' AND '20180525'
	 GROUP BY 종목코드, 종목명
	) A LEFT OUTER JOIN
	(
	SELECT 종목코드, 종목명 , 종가
	  FROM 거래_일 C 
	 WHERE  C.일자 ='20180525'
	) B
	ON A.종목코드 = B.종목코드
	
 WHERE 

SELECT *
  FROM 거래_일 C 
 WHERE  C.일자 ='20180525'
 GROUP BY 종목코드, 종목명
 
 
	 SELECT * FROM 기업개요
 WHERE 개요 LIKE '%반도체%' 
 
 
 
 SELECT 종목코드, 종목명 FROM 종목 
 
SELECT COUNT(*) FROM 거래_일 
 
SELECT * FROM 거래_일 WHERE 일자 = '20180713'


