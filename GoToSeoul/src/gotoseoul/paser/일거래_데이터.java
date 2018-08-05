package gotoseoul.paser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import gotoseoul.BaseClass;

public class 일거래_데이터 extends BaseClass
{
	int MAX_PAGE_SIZE = 300;
	int SLEEP_MS = 1; // 한 종목 수집 후 SLEEP 시간.
	
	public static void main(String[] args) throws Exception
	{
		일거래_데이터 app = new 일거래_데이터();
		
		app.init();
	}
	
	public void init() throws Exception
	{
		String url = "http://finance.daum.net/item/foreign_yyyymmdd.daum?";
				
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		String sql1 = " SELECT 종목코드, 종목명 FROM 종목 ";
		
		String sql2 = "INSERT INTO `거래_일` ( `종목코드`, `종목명`, `일자`, `외인_보유수`, `외인_지분율`, `외인_순매수`, `기관_순매수`, `종가`, `전일비`, `등락률`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		String 해당일만_추출 = ""; // 예> 20180305
		String searchDate = "20180528";		// 지정일 이후 데이터 데이터 추출. 예> 20180305
		boolean isBreak = false;

		String 종목코드 = null;
		String 종목명 = null;
		
		int idx = 1;
		String str = null;
		String data = "";
		
		conn = getConn();
		conn.setAutoCommit( false);
		
		ps1 = conn.prepareStatement( sql1 );
		rs = ps1.executeQuery();
		
		ps2 = conn.prepareStatement( sql2 );
		
		while( rs.next() )
		{
			try
			{
				종목코드 = rs.getString( "종목코드" );
				종목명 = rs.getString( "종목명" );
				
				System.out.println( url + 종목코드);
				
				for( int k=1; k<MAX_PAGE_SIZE; k++ )
				{
					Document doc = Jsoup.connect( url + "page="+k+"&code=" + 종목코드).get();

					Elements selectData = doc.select("table.dTable tr");
					
					// 데이터가 없는 것으로 보고, 중지한다.
					if( selectData.text().length() < 50 )
						break;
					
					for (Element el : selectData )
					{
						data =  el.text();
						idx = 1;
						
						if( data.indexOf( "." ) == 2 )
						{
							String []pData = data.split( " " );
							
							for( int i=0; i<pData.length; i++ )
							{
								str =  pData[i];
								
								if( 해당일만_추출.trim().equals( "" ) )
								{
									if( i == 0 ) 
									{
										// 해당 기간 만큼 받고, 중지.
										if(
												searchDate.equals( "20"+str.replaceAll( "\\.", "") )
												||
												Integer.parseInt( searchDate ) > Integer.parseInt(  "20"+str.replaceAll( "\\.", "") )		
										)
										{
											isBreak = true;
										}
										else 
										{
											ps2.setString( idx++,  종목코드 );
											ps2.setString( idx++,  종목명 );
											ps2.setString( idx++,   "20"+str.replaceAll( "\\.", "") );
										}
									}
									else 
									{
										ps2.setString( idx++,   
												str.replaceAll( "%", "").replaceAll( "▼", "-").replaceAll( "↓", "-").replaceAll( "↑", "+").replaceAll( "▲", "+").replaceAll( ",", "")  );
									}
								}
								else 
								{
									if( 해당일만_추출.trim().equals("20"+str.replaceAll( "\\.", "") ) )
									{
										if( i == 0 ) 
										{
											ps2.setString( idx++,  종목코드 );
											ps2.setString( idx++,  종목명 );
											ps2.setString( idx++,   "20"+str.replaceAll( "\\.", "") );
										}
										else 
										{
											ps2.setString( idx++,   
													str.replaceAll( "%", "").replaceAll( "▼", "-").replaceAll( "↓", "-").replaceAll( "↑", "+").replaceAll( "▲", "+").replaceAll( ",", "")  );
										}
										
										isBreak = true;
									}
								}
							}
							
							ps2.addBatch();
						}
						
						if( isBreak )
							break;
					}
					
					if( k%100 == 0)
					{
						ps2.executeBatch();
					}
					
					if( isBreak )
						break;
				}
				
				isBreak = false; // 다른 종목
				
				// 종목 단위로 커밋 처리.
				ps2.executeBatch();
				conn.commit();
				
				Thread.sleep( SLEEP_MS );
			}
			catch( Exception ie )
			{
				System.out.println( data );
				ie.printStackTrace();
			}
		}
		
		ps2.executeBatch();
		conn.commit();
		conn.setAutoCommit( true);
		
		if( rs != null) rs.close();
		if( ps1 != null) rs.close();
		if( ps2 != null) rs.close();
		if( conn != null) rs.close();
	}
	
}
