import requests  # 웹 페이지 요청을 위한 라이브러리
import json  # JSON 데이터 처리를 위한 라이브러리
from bs4 import BeautifulSoup  # HTML/XML 파싱을 위한 라이브러리


def get_full_url(relative_url):
    base_url = 'https://star.ohmynews.com'
    return base_url + relative_url if relative_url.startswith('/') else relative_url

def get_news():
    # 대상 웹 페이지 URL
    url = 'https://m.ohmynews.com/NWS_Web/Mobile/at_list.aspx?TAG_NM=%EC%9D%B8%EB%94%94%EB%B0%B4%EB%93%9C'

    # 웹 요청을 위한 헤더 정보
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36'
    }

    # 지정된 URL로부터 응답 받기
    response = requests.get(url, headers=headers)  # headers were missing in your GET request
    # 응답 내용을 BeautifulSoup 객체로 파싱
    soup = BeautifulSoup(response.text, 'html.parser')

    # 뉴스 정보가 담긴 리스트 찾기
    newsInfoList = (soup.find('ul', attrs={'class': 'list_thumbandstat'}).find_all('li')) \
        if soup.find('ul', attrs={'class': 'list_thumbandstat'}) \
        else []

    # 1. soup.find('ul', attrs={'class': 'list_thumbandstat'}):
    #    BeautifulSoup 객체인 soup을 사용하여 HTML에서 class 속성이 'movie_list'인 <ol> 태그를 찾습니다.
    #    이 <ul> 태그는 뉴스 목록을 나타내는 컨테이너 역할을 합니다.

    # 2. .find_all('li'):
    #    찾은 <ul> 태그 내에서 모든 <li> 태그를 찾습니다.
    #    이 <li> 태그들은 인디별 뉴스 정보를 나타냅니다.

    # 3. if soup.find('ul', attrs={'class': 'list_thumbandstat'}):
    #    이 조건문은 'list_thumbandstat' 클래스를 가진 <ul> 태그가 실제로 존재하는지 확인합니다.
    #    만약 해당 태그가 없다면, movieInfoList는 빈 리스트로 초기화됩니다.

    # 뉴스 데이터를 저장할 리스트
    news_data = []

    # 각 뉴스 정보 추출
    for newsInfo in newsInfoList:
        # 인디 이미지, 제목, 좋아요 갯수 정보 추출
        newsImg = newsInfo.find('img')
        newsTitle = newsInfo.find('span', attrs={'class': 'tit'})
        newsLike = newsInfo.find('em', attrs={'class': 'rec-i'})
        newsLink = newsInfo.find('a', href=True) # href 속성이 있는 a 태그 찾기
        full_url = get_full_url(newsLink['href']) if newsLink else "X"


        # 추출한 정보를 딕셔너리 형태로 movie_data 리스트에 추가
        news_data.append({
            'image': newsImg['src'] if newsImg else "X",
            'title': newsTitle.get_text().strip() if newsTitle else "X",
            'likes': newsLike.get_text() if newsLike else "X",
            'link': full_url
        })

    # 추출된 뉴스 데이터를 JSON 형식으로 변환
    json_data = json.dumps(news_data, ensure_ascii=False, indent=4)
    # JSON 데이터 출력
    print(json_data)
    # JSON 데이터 반환
    return json_data


get_news()

