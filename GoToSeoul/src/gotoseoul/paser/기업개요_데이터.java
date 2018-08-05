package gotoseoul.paser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import gotoseoul.BaseClass;

public class 기업개요_데이터 extends BaseClass
{
	int MAX_PAGE_SIZE = 300;
	int SLEEP_MS = 1; // 한 종목 수집 후 SLEEP 시간.
	
	public static void main(String[] args) throws Exception
	{
		기업개요_데이터 app = new 기업개요_데이터();
		
		app.init();
	}
	
	public void init() throws Exception
	{
		String url = "http://companyinfo.stock.naver.com/v1/company/c1010001.aspx?cn=&cmp_cd=";
				
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		String sql1 = " SELECT 종목코드, 종목명 FROM 종목 ";
		
		String sql2 = "INSERT INTO `기업개요` ( `종목코드`, `종목명`, `개요`) VALUES ( ?, ?, ? )";
		
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
				
				Document doc = Jsoup.connect( url + 종목코드).get();

				Elements selectData = doc.select("div.cmp_comment");
				
				// 데이터가 없음.
				if( selectData.text().length() < 50 )
					continue;
				
				for (Element el : selectData )
				{
					idx = 1;

					ps2.setString( idx++,  종목코드 );
					ps2.setString( idx++,  종목명 );
					ps2.setString( idx++,  el.text() );
					
					ps2.executeUpdate();
				}
			}
			catch( Exception ie )
			{
				System.out.println( data );
				ie.printStackTrace();
			}
			
			Thread.sleep( SLEEP_MS );
		}
		
		if( rs != null) rs.close();
		if( ps1 != null) rs.close();
		if( ps2 != null) rs.close();
		if( conn != null) rs.close();
	}
}