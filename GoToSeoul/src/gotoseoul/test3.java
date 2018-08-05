package gotoseoul;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test3
{

	public static void main(String[] args) throws IOException
	{

		String url = "http://wisefn.stock.daum.net/company/c1020001.aspx?cmp_cd=000660";

		Document doc = Jsoup.connect(url).get();

		Elements selectData = doc.select("td");
		String data = "";
		
		int 개발비_로우수 = 0;
		int ii = 1;
		
		for (Element el : selectData )
		{
			data =  el.text();
			
			// 주요 정보 1.
			if( el.className().trim().equals( "" ) )
			{
				if( data.indexOf( "결산기" ) > -1 )
				{
					String strs[] = data.split( "\\|" );
					
					for( String str : strs )
					{
						System.out.println( str.trim() );
					}
				}
			}
			// 주요 정보 2.
			else if( 8 > ii && ( el.className().trim().equals( "c4 txt" ) ||  el.className().trim().equals( "c2 txt" ) ) )
			{
				ii++;
				System.out.println( el.className()+ "##" + data);
			}
			// 개발비. 3년.
			else if( el.className().indexOf( " line " ) > -1 )
			{
				System.out.println( el.className()+ "##" + data);
			}
			else if( el.className().trim().equals( "c8 center" ) )
			{
				++개발비_로우수;
			}
			
			if( 개발비_로우수 > 2 )
				break;
			
		}
	}

}
