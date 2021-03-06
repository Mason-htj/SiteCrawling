## Site Crawling Example

<br>

### 1. 뉴스 불러오기 (NewsActivity)

**네이버 뉴스** 페이지 오른쪽 상단과, **다음 뉴스 사회** 페이지에 있는 많이 본 뉴스 리스트를 가져와 보여준다.  옵션메뉴 클릭시 어떤 포털의 뉴스를 볼 지 설정할 수 있으며, 설정 정보는 sharedPreference에 저장된다.

Top3 의 경우 ViewType을 구분하여 ViewHolder를 다르게 표시했다.

아이템 클릭시 해당 뉴스기사를 볼 수 있다. (Intent 구동 방식)

Data source : https://news.naver.com/, https://media.daum.net/society/

<br>

#### 스크린샷

<p align="center"><img src="/screenshots_news_naver.jpg"></p>

<p align="center"><img src="/screenshots_news_daum.jpg"></p>

_다음 포털의 경우 탭별로 뉴스기사가 30개씩 존재한다._

<br>

<br>

_아래는 사용하지 않는 액티비티로, 앱 구동시 보이지않는다_

### 2. 이미지 목록 불러오기 (MainActivity)

웹사이트에 있는 이미지들을 크롤링하여 앱의 리사이클러뷰에 띄워준다.

Image source : http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx

<br>

#### 스크린샷

<p align="center"><img src="/screenshots_main.jpg"></p>

<br>

#### 파싱한 방법

`Jsoup` 라이브러리를 활용하여 웹페이지의 `html`을 파싱한다.

- gradle에 `implementation 'org.jsoup:jsoup:1.11.2'` 추가하기

- AsyncTask 클래스 정의하기 (Jsoup 파싱은 worker thread 에서만 동작)

  - 주의사항 : html 에서 클래스명에 **공백(whitespace)이 포함되어있을 경우 . 로 교체**

  ```kotlin
  val doc = Jsoup.connect(url).get()
  val elements = doc.select("div.gallery-item-group.exitemrepeater")
  for (element in elements) {
      data.add(ImageItem(
          element.select("div.gallery-item-caption").text().trim(), 	// 이미지 제목
          element.select("img").attr("abs:src").trim()))              // 이미지 썸네일
  ```

<br>

#### To do list

- ~~inner class 로 선언한 AsyncTask 클래스 warning 제거~~
- gradle 정리
- 가능하다면 페이징 구현해보기(왠지 불가능할 것 같긴 하다.)

<br>

<br>