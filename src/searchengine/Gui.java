package searchengine;

import searchengine.model.DbService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gui extends JFrame implements KeyListener {

	public static void main(String[] args) {
	}

		//TODO: Noktalama isaretlerinden sadece
		//TODO: Çıktı kümesi kendi içinde değil sadece diğer kümeler ile de alfabetik olarak sıralanmali
        //TODO: Birde URL indeksleme de ve çıktı da daha önceki oluşturulanlar da db'den çekilerek gösterilmeli
		//TODO: Index goruntulemede sadece runtime'da 	olusturulanlar goruntuleniyor.
		//TODO: cıktı ekraninda her seferinde eklenenler yok tumden itibaren eklenenlerden baslanarak gosteriliyor bu pek
		//TODO: hos bir durum degil. Eger ki cikti ekraninda sadece yeni olusturulan index ile ilgili goruntulemeler yapilacaksa
		//TODO: acayip derecede aptalca bir durum oluyor bu.
		//TODO: Index goruntuleme ekraninda ise db'den baslangictan itibaren olan her sey cekilip goruntulenmeli
		//NOT metin girisi'nde butona basilinca cikti ve index goruntulenme ekranları olusturuluyor. Bunu baska turlu halletmek
		// daha mantıklı olabilir yani ekranlar arasında gecis yapilinca. Hadi cikti ekrani mantikli olabilir sadece isleme sokulan metin ile
		// ilgili ciktilari barindiracaksa ama index goruntuleme butun indexleri gosterecekse bastan sona buttona her basildiginda bastan gostermesi yerine bir kere cekilir datalar
		// sonra ekleme yapılır surekli data cekilmektense
		// ayrica cikti ekraninda surekli ekleme yapmak yani ilk eklenen metinden itibaren son eklenene kadar olan butun ciktilar yerine sadece en son girilen
		// metne ait shifted ciktisi goruntulenmesi daha mantikli olur
		// Index goruntuleme ekrani her daim temizlenmeden kalir ilk program acilinca ilklenir ve icerigi olusturulur ardindan sadece yeni veriler eklenir oraya
		// ayrica scroll edilme ozelligi eklenirse guzel durur o tab icin.
		// cikti ekraninda ise sadece en basindan beri olan veriler deil de en sonda olan veriler olmali ve bu ekran sadece icerik olarak butona basilinca olusturulmali metin girisindeki butona

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}
}
