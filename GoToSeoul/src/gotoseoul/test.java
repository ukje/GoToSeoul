package gotoseoul;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class test
{

	public static void main(String[] args) throws IOException
	{

		String url = "http://wisefn.stock.daum.net/company/cF5004.aspx?cmp_cd=000660";

		Document doc = Jsoup.connect(url).get();

		Elements scripts = doc.select("script");

		for (Element script : scripts)
		{
			Pattern p = Pattern.compile("dest(.*)");
			Matcher m = p.matcher(script.html());
			
			m.find();
			m.find();

			// 연간
			if (m.find())
			{
				if (m.group(1).length() > 200)
				{
					Pattern pp = Pattern.compile("\\[(.*?)\\]");
					Matcher mm = pp.matcher(m.group(1));

					while (mm.find())
					{
						String data = mm.group(1).replaceAll( "\\[", "" ).replaceAll( ",", "" );
						
						String []pData = data.split( "\"\"" );
						
						for( int i=0; i<pData.length; i++ )
						{
							// FY_End
							if( i == 0 )
							{
								
							}
							
							System.out.println( pData[i].replaceAll( "\"", "" ) );
						}
						
						
					}
				}
			}

			// 분기
			if (m.find())
			{
				if (m.group(1).length() > 200)
				{
					Pattern pp = Pattern.compile("\\[(.*?)\\]");
					Matcher mm = pp.matcher(m.group(1));

					while (mm.find())
					{
						System.out.println("##" + mm.group(1)); // value only

					}
				}
			}
		}
	}

}
