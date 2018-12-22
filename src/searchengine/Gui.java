package searchengine;

import java.awt.*;
import java.sql.SQLException;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Gui extends JFrame implements KeyListener {
	DbService dbService = new DbService();
	static private final String newline = "\n";

	private JPanel createIndexPanel;
    private JTextField urlField;
    private JTextField sentenceField;
    private JButton createButton;

	private JPanel outputPanel;
	private JPanel urlIndexingPanel;

	private JPanel searchPanel;
    private JTextField searchField;
    private JTextField urlResult;
    private JScrollPane jScrollPane1;
    private JList<String> searchResults;
	private JButton searchButton;

	ArrayList<Sentence> sentenceList;

	static String fullSentence="";

	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
			try {
				Gui frame = new Gui();
				frame.setSize(1500, 600);
				frame.setVisible(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Gui() {
		Engine searchEngine = new Engine();

		sentenceList = new ArrayList<Sentence>();

	    setTitle("Metin Indeksleme");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		searchPanel = new JPanel(new BorderLayout());

		searchResults = new JList<String>();
        urlResult = new JTextField("Url:");
        searchField = new JTextField("Aranacak kelimeleri giriniz");
        jScrollPane1 = new JScrollPane();
		searchButton = new JButton("Ara");


		searchButton.addActionListener( e -> {
			String searchSentence = searchField.getText().toString();

			HashMap<String, ArrayList<String>> searchResult = searchEngine.searchUrl(searchSentence);

			if(searchResult == null){
				return;
			}

			ArrayList<String> urls = searchResult.get("url");
			ArrayList<String> contents = searchResult.get("content");

			for(int i = 0; i < urls.size(); i++){
				contents.set(i, "Content: " + contents.get(i) + " Url: " + urls.get(i));
				System.out.println(contents.get(i));
			}

			searchResults.removeAll();
			searchResults.setListData(contents.toArray(new String[0]));
		});

        jScrollPane1.setViewportView(searchResults);

        urlResult.setEditable(false);

        searchPanel.add(searchField, BorderLayout.PAGE_START);
		searchPanel.add(searchResults, BorderLayout.CENTER);
        searchPanel.add(urlResult, BorderLayout.PAGE_END);
		searchPanel.add(searchButton, BorderLayout.LINE_END);

        createIndexPanel = new JPanel(new BorderLayout());

        urlField = new JTextField("Url giriniz");
        sentenceField = new JTextField("Metin giriniz");
        createButton = new JButton("olustur");

        JTabbedPane tappedPane = new JTabbedPane();

        createIndexPanel.add(urlField,BorderLayout.PAGE_START);
        createIndexPanel.add(sentenceField,BorderLayout.CENTER);
        createIndexPanel.add(createButton,BorderLayout.PAGE_END);

        outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(20,1));

        urlIndexingPanel = new JPanel();
        urlIndexingPanel.setLayout(new GridLayout(20,1));

	     createButton.addActionListener(e -> {
	     	sentenceList = new ArrayList<Sentence>();

			 outputPanel.removeAll(); // Cikti ekrani temizlenir

	     	String url = urlField.getText();
			 fullSentence = sentenceField.getText();
				String [] parts = fullSentence.split("\\."); // Burada noktayı kaldırıyorlar. Onun yerine tüm punctutationlar kaldırılmalı.
				//System.out.println(String.join(" ", parts));
				for(int i = 0; i < parts.length; i++) {
					String[] words = parts[i].split("\\s+"); // cumleyi kelime kelime ayırıyorlar.
					for (int j = 0; j< words.length; j++) {
						// You may want to check for a non-word character before blindly
						// performing a replacement
						// It may also be necessary to adjust the character class
						words[j] = words[j].replaceAll("[^\\w]", "");
					}
					Sentence wordiex = new Sentence(words, url);
					try{
						int content_id = dbService.saveFullSentence(wordiex);
						dbService.saveShiftedSentences(wordiex.getShiftedSentences(), content_id);
					}catch(SQLException exception){ //Eğer bu hata yenirse db de eklenemedi tarzından uyarı verilecek. ama sistem çaısmaya devam edecek
						exception.printStackTrace();
					}
					sentenceList.add(wordiex);
				}


				for(int i = 0; i < sentenceList.size(); i++) {
				    searchEngine.addSentence(sentenceList.get(i));
				}

				//searchEngine.listItems();
                searchEngine.sort();
				/*��kt� sayfas�nda for i�inde print i�lemini buraya yap*/
				for(int i = 0; i < searchEngine.getCountSort(); i++) {
				    JTextField text3 = new JTextField();
				    text3.setBackground(Color.gray);
				    text3.setText("Index: " + searchEngine.getIndex(i) + " Url: " + url);
				    text3.setEditable(false);

                    outputPanel.add(text3);
                    outputPanel.revalidate();
                    outputPanel.repaint();
				}

				searchEngine.clearAllFields(); // searcEngine'i countsort ve count degiskenlerinden kurtarırsak buna gerek kalmayabilir gerci arraylistlerini gene temizlememiz gerek o yüzden bu gerekli
			});

		tappedPane.add(searchPanel, "Arama");
		tappedPane.add(createIndexPanel,"Metin Girisi");
		tappedPane.add(outputPanel,"Cıktı");
		tappedPane.add(urlIndexingPanel,"Index goruntuleme");

		tappedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) { // burada tablar arasında geçiş yapıldığında verilerin silinme islemi yapilmasi gerekiyor
				String activeTabTitle = tappedPane.getTitleAt(tappedPane.getSelectedIndex());

				System.out.println(activeTabTitle);
				// Search Ekranı temizleniyor
				if(activeTabTitle != tappedPane.getTitleAt(0)){
					searchResults.removeAll();
					searchResults.setListData(new String[0]);

					urlResult.setText("Url:");
					searchField.setText("Aranacak kelimeleri giriniz");
				}

				if(activeTabTitle != tappedPane.getTitleAt(1)) { // Index olusturma ekranını baslangica dondurmek icin
					urlField.setText("Url giriniz");
					sentenceField.setText("Metin giriniz");
				}

				if(activeTabTitle == tappedPane.getTitleAt(3)){ // Index goruntuleme ekrani
					ArrayList<String> outputList = searchEngine.getAllIndexesWithUrl();

					urlIndexingPanel.removeAll();

					if(outputList == null){
						return;
					}


					for(int i= 0 ; i < outputList.size(); i++) {
				    	JTextField text4 = new JTextField();
				    	text4.setBackground(Color.gray);
				    	text4.setText(outputList.get(i));
                    	text4.setEditable(false);

				    	urlIndexingPanel.add(text4);
                    	urlIndexingPanel.revalidate();
                    	urlIndexingPanel.repaint();
					}
				}
			}
		});

		add(tappedPane);

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
	}

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
