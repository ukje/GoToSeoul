package gotoseoul.paser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import gotoseoul.BaseClass;

public class 종목상세_데이터 extends BaseClass
{
	int MAX_PAGE_SIZE = 300;
	
	public static void main(String[] args) throws Exception
	{
		종목상세_데이터 app = new 종목상세_데이터();
		
		app.init();
	}
	
	public void init() throws Exception
	{
		String url = "http://wisefn.stock.daum.net/company/c1020001.aspx?cmp_cd=";
				
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		String sql1 = " SELECT 종목코드, 종목명 FROM 종목"; //  LIMIT 0, 1
		
		String sql2 = "INSERT INTO `종목_상세` ( `종목코드`, `종목명`, `상장구분`, `업종`, `WICS`, `EPS`, `BPS`, `PER`, `업종PER`, `PBR`, `현금배당수익률`, `결산기`, `설립일`, `상장일`, `계열`, `종업원수`, `발행주식수`, `개발비1_회계연도`, `개발비1_지출총액`, `개발비1_매출비율`, `개발비2_회계연도`, `개발비2_지출총액`, `개발비2_매출비율`, `개발비3_회계연도`, `개발비3_지출총액`, `개발비3_매출비율` ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ;";
		
		String 종목코드 = null;
		String 종목명 = null;
		
		int idx = 1;
		String data = "";
		
		conn = getConn();
		
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
				
				Document doc = Jsoup.connect(url + 종목코드).get();
				Elements selectData = doc.select("td");
				
				int 개발비_로우수 = 0;
				int ii = 1, jj=1;
				idx = 1;

				for (Element el : selectData )
				{
					data =  el.text();

					// 주요 정보 1.
					if( el.className().trim().equals( "" ) )
					{
						if( data.indexOf( "결산기" ) > -1 )
						{
							String strs[] = data.split( "\\|" );

							for( int i=0; i<strs.length; i++ )
							{
								 String str = strs[i];
								 
								 if( i==2 )
								 {
									 ps2.setString( idx++,  종목코드 );
									 ps2.setString( idx++,  종목명 );
									 ps2.setString( idx++,  str.substring(0, str.indexOf(":" ) ).trim() );
									 ps2.setString( idx++,  str.substring( str.indexOf(":" ) + 1, str.length() ).trim() );
								 }
								 else  if( i==3 )
								 {
									 ps2.setString( idx++,  str.substring( str.indexOf( "WICS : " )+7, str.indexOf("EPS" ) ).trim() );
									 
									 try
									 {
										 Double.parseDouble(str.substring( str.indexOf( "EPS" )+4, str.length() ).trim().replaceAll( ",", "" ) );
										 ps2.setString( idx++,  str.substring( str.indexOf( "EPS" )+4, str.length() ).trim().replaceAll( ",", "" ) );
									 }
									 catch( Exception e )
									 {
										 ps2.setString( idx++, "0" );
									 }
								 }
								 // BPS
								 else  if( i==4 )
								 {
									 try
									 {
										 Double.parseDouble(str.substring( str.indexOf( "BPS " )+4, str.length() ).trim().replaceAll( ",", "" ));
										 ps2.setString( idx++,  str.substring( str.indexOf( "BPS " )+4, str.length() ).trim().replaceAll( ",", "" ) );
									 }
									 catch( Exception e )
									 {
										 ps2.setString( idx++, "0" );
									 }
								 }
								 // PER
								 else  if( i==5 )
								 {
									 if( "".equals( str.substring( str.indexOf( "PER " )+4, str.length() ).trim().replaceAll( ",", "" ) ) )
										 ps2.setString( idx++, "0" );
									 else 
										 ps2.setString( idx++, str.substring( str.indexOf( "PER " )+4, str.length() ).trim().replaceAll( ",", "" ) );
								 }
								 // 업종PER
								 else  if( i==6 )
								 {
									 if( "".equals( str.substring( str.indexOf( "PER " )+4, str.length() ).trim().replaceAll( ",", "" ) ) )
										 ps2.setString( idx++, "0" );
									 else 
										 ps2.setString( idx++, str.substring( str.indexOf( "PER " )+4, str.length() ).trim().replaceAll( ",", "" ) );
								 }
								 // PBR
								 else  if( i==7 )
								 {
									 if( "".equals( str.substring( str.indexOf( "PBR " )+4, str.length() ).trim().replaceAll( ",", "" ) ) )
										 ps2.setString( idx++, "0" );
									 else
										 ps2.setString( idx++, str.substring( str.indexOf( "PBR " )+4, str.length() ).trim().replaceAll( ",", "" ) );
								 }
								// 현금배당수익률
								 else  if( i==8 )
								 {
									 try
									 {
										 Double.parseDouble( str.substring( str.indexOf( "현금배당수익률 " )+8, str.indexOf("결산기" ) ).trim().replaceAll( "%", "" ) );
										 
										 ps2.setString( idx++, str.substring( str.indexOf( "현금배당수익률 " )+8, str.indexOf("결산기" ) ).trim().replaceAll( "%", "" ) );
										 ps2.setString( idx++, str.substring( str.indexOf( " : " )+3, str.length() ).trim() );
									 }
									 catch( Exception e )
									 {
										 ps2.setString( idx++, "0" );
										 ps2.setString( idx++, str.substring( str.indexOf( " : " )+3, str.length() ).trim() );
									 }
								 }
								 else 
								 {
									// System.out.println( el.className()+ "##" + str);
								 }
							}
						}
					}
					// 주요 정보 2.
					else if( 8 > ii && ( el.className().trim().equals( "c4 txt" ) ||  el.className().trim().equals( "c2 txt" ) ) )
					{
						ii++;

						// 설립일, 상장일
						if (ii == 4)
						{
							ps2.setString( idx++, data.substring(0, 10).replaceAll( "/", "" ));
							ps2.setString( idx++, data.substring(data.indexOf(":") + 2, data.indexOf(")")).replaceAll( "/", "" ));
						}
						// 계열
						else if (ii == 6)
						{
							ps2.setString( idx++, data.trim());
						}
						// 총업원수
						else if (ii == 7)
						{
							try
							{
								ps2.setString(idx++, data.substring(0, data.indexOf(" ")).trim().replaceAll(",", ""));
							}
							catch (Exception e)
							{
								ps2.setString(idx++, "0");
							}
						}
						// 발행주식수
						else if (ii == 8)
						{
							ps2.setString( idx++, data.substring(0, data.indexOf(" ")).trim().replaceAll(",", ""));
						}
						else
						{
							// System.out.println( el.className()+ "##" + data);
						}
					}
					// 개발비. 3년.
					else if( el.className().indexOf( " line " ) > -1 )
					{
						jj++;
						
						if (jj == 2)
						{
							ps2.setString(idx++, data.trim());
						}
						else if (jj == 3)
						{
							try
							{
								Integer.parseInt(data.replaceAll(",", "").trim());
								ps2.setString(idx++, data.replaceAll(",", "").trim());
							}
							catch (Exception e)
							{
								ps2.setString(idx++, "0");
							}
						}
						else if (jj == 4)
						{
							try
							{
								Double.parseDouble( data.trim());
								ps2.setString(idx++, data.trim());
							}
							catch (Exception e)
							{
								ps2.setString(idx++, "0");
							}
						}
						else
						{
							// System.out.println( el.className()+ "##" + data);
						}
						
					}
					else if( el.className().trim().equals( "c8 center" ) )
					{
						if(  data.trim().equals("") )
							break;
							
						++개발비_로우수;
						jj = 1;
					}
					
					if( 개발비_로우수 > 2 )
						break;
				}
			}
			catch( Exception ie )
			{
				System.out.println( data );
				ie.printStackTrace();
			}
			
			try
			{
				// 개발비 공백으로 채우기.
				if( idx != 16)
				{
					for( int k=idx; k<17; k++ )
						ps2.setString(k++, "0");
				}
				
				ps2.executeUpdate();
			}
			catch( Exception ie )
			{
				System.out.println( data );
				ie.printStackTrace();
			}
		}
		
		if( rs != null) rs.close();
		if( ps1 != null) rs.close();
		if( ps2 != null) rs.close();
		if( conn != null) rs.close();
	}
}
