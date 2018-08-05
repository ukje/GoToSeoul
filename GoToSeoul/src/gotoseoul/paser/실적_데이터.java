package gotoseoul.paser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import gotoseoul.BaseClass;

public class 실적_데이터 extends BaseClass
{

	public static void main(String[] args) throws Exception
	{
		실적_데이터 app = new 실적_데이터();
		
		app.init();
	}
	
	public void init() throws Exception
	{
		String url = "http://wisefn.stock.daum.net/company/cF5004.aspx?cmp_cd=";
		
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		
		String sql1 = " SELECT 종목코드, 종목명 FROM 종목";
		
		String sql2 = "INSERT INTO `실적_연간` ( `종목코드`, `종목명`, `FY_End`, `실적연도`, `FY구분`, `주재무제표`, `매출액`, `YoY`, `영업이익`, `당기순이익`, `EPS`, `BPS`, `PER`, `PBR`, `ROE`, `EV_EBITDA` )  VALUES (  ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
		
		String sql3 = "INSERT INTO `실적_분기` ( `종목코드`, `종목명`, `FY_End`, `실적연도`, `실적분기`, `FY구분`, `주재무제표`, `매출액`, `YoY`, `영업이익`, `당기순이익`, `EPS`, `BPS`, `PER`, `PBR`, `ROE`, `EV_EBITDA` )  VALUES (  ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
		
		String 종목코드 = null;
		String 종목명 = null;
		int idx = 1;
		String str = null;
		
		conn = getConn();
		
		ps1 = conn.prepareStatement( sql1 );
		rs = ps1.executeQuery();
		
		ps2 = conn.prepareStatement( sql2 );
		ps3 = conn.prepareStatement( sql3 );
		
		while( rs.next() )
		{
			try
			{
				종목코드 = rs.getString( "종목코드" );
				종목명 = rs.getString( "종목명" );
				
				Document doc = Jsoup.connect( url + 종목코드).get();
				System.out.println(  url + 종목코드 + 종목명 );

				Elements scripts = doc.select("script");

				for (Element script : scripts)
				{
					Pattern p = Pattern.compile("dest(.*)");
					Matcher m = p.matcher(script.html());
					
					m.find(); // 
					m.find();

					// 연간 #############################################################
					if (m.find())
					{
						Pattern pp = Pattern.compile("\\[(.*?)\\]");
						Matcher mm = pp.matcher(m.group(1));
						
						// 최근 5년 실적.
						while (mm.find())
						{
							String data = mm.group(1).replaceAll( "\\[", "" ).replaceAll( ",", "" );

							if( data.trim().length() < 10 )
								continue;
							
							String []pData = data.split( "\"\"" );
							idx = 1;
							
							// 연도별 실적 데이터.
							for( int i=0; i<pData.length; i++ )
							{
								str = pData[i].replaceAll( "\"", "" );
								
								if( i == 0 ) 
								{
									ps2.setString( idx++,  종목코드 );
									ps2.setString( idx++,  종목명 );
									
									ps2.setString( idx++,  str.substring( 0, str.indexOf("(")).replaceAll("\\.", "" ) ); // FY_End
									ps2.setString( idx++,  str.substring( 0, 4) ); // 실적연도
									ps2.setString( idx++,  str.substring( str.indexOf("(")+1, str.indexOf("(")+2  ) ); // FY구분(실적(A), 예상(E))
								}
								else 
								{
									try
									{
										Integer.parseInt( str );
										ps2.setString( idx++,  str );
									}
									catch( Exception ie )
									{
										ps2.setString( idx++,  "0" );
									}									
								}
								
								ps2.executeUpdate();
							}
						}
					}
				}
			}
			catch( Exception ie )
			{
				System.out.println( str );
				ie.printStackTrace();
			}
		}
		
		if( rs != null) rs.close();
		if( ps1 != null) rs.close();
		if( ps2 != null) rs.close();
		if( ps3 != null) rs.close();
		if( conn != null) rs.close();
	}
	
}
