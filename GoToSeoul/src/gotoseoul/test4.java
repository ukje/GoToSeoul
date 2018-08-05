package gotoseoul;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;

public class test4
{
	public static void main(String[] args) throws Exception
	{
		String url = "http://wisefn.stock.daum.net/company/cF1001.aspx?finGubun=MAIN&cmp_cd=052260";

		Document doc = Jsoup.connect(url).get();
		
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

						System.out.println( 연간 );
						System.out.println( 분기 );
						
						String 연간s[] = 연간.toString().replace( "[", "" ).replace( "]", "" ).trim().split( ", " );
						String 분기s[] = 분기.toString().replace( "[", "" ).replace( "]", "" ).trim().split( ", " );
						
						if( 연간s != null && 연간s.length == 5 )
						{
							
							System.out.print( 연간s[0] );
							System.out.print( 연간s[1] );
							System.out.print( 연간s[2] );
							System.out.print( 연간s[3] );
							System.out.print( 연간s[4] );
							
							System.out.print( 분기s[0] );
							System.out.print( 분기s[1] );
							System.out.print( 분기s[2] );
							System.out.print( 분기s[3] );
						}
						
						// System.out.println( ii +"::" + str.toString().replace( "[", "" ).replace( "]", "" ).trim() );
							
					}
				}
			}
		}
	}
}
