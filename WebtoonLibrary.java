package WebtoonApp;

import java.util.ArrayList;
import java.util.List;

public class WebtoonLibrary {
	private List<Webtoon> webtoons;
	
	public WebtoonLibrary() {
		this.webtoons = new ArrayList<>();
	}
	
	public List<Webtoon> search(String keyword) {
		List<Webtoon> result = new ArrayList<>();
		
		for (int i = 0; i < webtoons.size(); i++) {
			Webtoon webtoon = webtoons.get(i);
			
			if (webtoon.getTitle().equalsIgnoreCase(keyword))
				result.add(webtoon);
		}
		return result;
	}
	
	public void addWebtoons(List<Webtoon> webtoonList) {
		this.webtoons.addAll(webtoonList);
	}
}