package gotoseoul.paser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;

import gotoseoul.BaseClass;

public class 재무_데이터 extends BaseClass
{

	public static void main(String[] args) throws Exception
	{
		재무_데이터 app = new 재무_데이터();
		
		app.init();
	}
	
	public void init() throws Exception
	{
		String url = "http://wisefn.stock.daum.net/company/cF1001.aspx?finGubun=MAIN&cmp_cd=";
		
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		String sql1 = " SELECT 종목코드, 종목명 FROM 종목";
		
		String sql2 = "INSERT INTO `재무` ( `종목코드`, `종목명`, `재무구분`, `연간1`, `연간2`, `연간3`, `연간4`, `분기1`, `분기2`, `분기3`, `분기4` )  VALUES (  ?,?,?,?,?,?,?,?,?,?,? )";
		
		String 종목코드 = null;
		String 종목명 = null;
		int idx = 1;
		String str = null;
		
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
				
				Document doc = Jsoup.connect( url + 종목코드).get();
				System.out.println(  url + 종목코드 + "-" + 종목명 );
				
				Element script = doc.select("script").get( 3 );

				String data =  script.html();
				data = data.substring( data.indexOf("var changeFinData =") + 20 );
				data = data.substring( 0, data.indexOf( ";" ) );
				
				Gson gson = new Gson();
				List[][][] dd = gson.fromJson( data , List[][][].class ); 
				
				// 재무 항목
				for( int i=0; i<dd.length; i++ )
				{
					 List[][] d1 = dd[i];
					 
					// 연, 분기 구분
					for( int ii=0; ii<d1.length; ii++ )
					{
						List[] d2 = d1[ii];
						
						if( ii == 0 )
						{
							for( int iii=0; iii<d2.length; iii++ )
							{
								Object 연간 = d2[iii] ;
								Object 분기 = d1[1][iii] ;
								
								//System.out.println( 종목코드 );
								//System.out.println( 연간 );

								String 연간s[] = 연간.toString().replace( "[", "" ).replace( "]", "" ).trim().split( ", " );
								String 분기s[] = 분기.toString().replace( "[", "" ).replace( "]", "" ).trim().split( ", " );
								
								if( 연간s != null  )
								{
									idx = 1;
									
									ps2.setString( idx++,  종목코드 );
									ps2.setString( idx++,  종목명 );
									ps2.setString( idx++,  연간s[0] );

									try { ps2.setDouble( idx, Double.parseDouble(연간s[1].replace(",", "")));} catch( Exception e ) { System.out.println(연간s[1] ); ps2.setInt( idx, 0 ); }idx++;
									try { ps2.setDouble( idx, Double.parseDouble(연간s[2].replace(",", "")));} catch( Exception e ) { ps2.setInt( idx, 0 ); }idx++;
									try { ps2.setDouble( idx, Double.parseDouble(연간s[3].replace(",", "")));} catch( Exception e ) { ps2.setInt( idx, 0 ); }idx++;
									try { ps2.setDouble( idx, Double.parseDouble(연간s[4].replace(",", "")));} catch( Exception e ) { ps2.setInt( idx, 0 ); }idx++;
									try { ps2.setDouble( idx, Double.parseDouble(분기s[0].replace(",", "")));} catch( Exception e ) { ps2.setInt( idx, 0 ); }idx++;
									try { ps2.setDouble( idx, Double.parseDouble(분기s[1].replace(",", "")));} catch( Exception e ) { ps2.setInt( idx, 0 ); }idx++;
									try { ps2.setDouble( idx, Double.parseDouble(분기s[2].replace(",", "")));} catch( Exception e ) { ps2.setInt( idx, 0 ); }idx++;
									try { ps2.setDouble( idx, Double.parseDouble(분기s[3].replace(",", "")));} catch( Exception e ) { ps2.setInt( idx, 0 ); }idx++;
									
									ps2.executeUpdate();
								}
								
								// System.out.println( ii +"::" + str.toString().replace( "[", "" ).replace( "]", "" ).trim() );
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
		if( conn != null) rs.close();
	}
	
}
