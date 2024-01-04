import requests
import json
from bs4 import BeautifulSoup


def get_product():
    product_info = []
    product_urls = [
        # gbbul_nocharim
        'https://marpple.shop/kr/gbbul_nocharim/products/14154311',
        'https://marpple.shop/kr/gbbul_nocharim/products/14154317',
        'https://marpple.shop/kr/gbbul_nocharim/products/14154327',
        'https://marpple.shop/kr/gbbul_nocharim/products/14154336',
        'https://marpple.shop/kr/gbbul_nocharim/products/14004110',
        'https://marpple.shop/kr/gbbul_nocharim/products/14154264',
        'https://marpple.shop/kr/gbbul_nocharim/products/14154234',
        'https://marpple.shop/kr/gbbul_nocharim/products/14154184',
        'https://marpple.shop/kr/gbbul_nocharim/products/14154065',
        'https://marpple.shop/kr/gbbul_nocharim/products/14154008',
        'https://marpple.shop/kr/gbbul_nocharim/products/14153869',
        'https://marpple.shop/kr/gbbul_nocharim/products/14130904',
        'https://marpple.shop/kr/gbbul_nocharim/products/14130600',
        # tvt
        'https://marpple.shop/kr/tvt/products/9022966',
        'https://marpple.shop/kr/tvt/products/10872638',
        'https://marpple.shop/kr/tvt/products/10872575',
        'https://marpple.shop/kr/tvt/products/10872588',
        'https://marpple.shop/kr/tvt/products/10872594',
        'https://marpple.shop/kr/tvt/products/10872604',
        'https://marpple.shop/kr/tvt/products/9022970',
        'https://marpple.shop/kr/tvt/products/9022975',
        'https://marpple.shop/kr/tvt/products/7849624',
        'https://marpple.shop/kr/tvt/products/7863551',
        'https://marpple.shop/kr/tvt/products/7863398'
    ]

    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36'
    }

    for url in product_urls:
        parts = url.split('/')
        try:
            name_index = parts.index('kr') + 1
            if name_index < len(parts):
                artist_name = parts[name_index]
            else:
                print("단어를 찾을 수 없습니다.")

            response = requests.get(url, headers=headers)
            # print(f"status code : {response.status_code}")
            soup = BeautifulSoup(response.text, 'html.parser')

            # 제품 정보 추출
            product_name = soup.find('h2', {'class': 'pd-product__name'}).get_text(strip=True)
            product_image = soup.find('img', {'class': 'mp-product-img'})['src']
            product_price = soup.find('span', {'class': 'pd-product__price'}).get_text(strip=True)

            product_info.append({
                'artist': artist_name,
                'name': product_name,
                'image': product_image,
                'price': product_price
            })

        except Exception as e:
            print(f"Error processing URL {url}: {e}")


    # JSON으로 변환 및 출력
    json_data = json.dumps(product_info, ensure_ascii=False, indent=4)
    print(json_data)
    for i in product_info:
        print(i)
    return json_data

get_product()







