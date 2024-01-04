from flask import Flask
from flask_cors import CORS
from routes.product import get_product
from routes.news import get_news

# Flask 인스턴스를 생성, __name__은 현재 실행 중인 모듈 이름을 전달하는 것
app = Flask(__name__)
# CORS를 설정, *는 모든 도메인에 대해서 허용.
CORS(app, origins=['*'])

@app.route('/')
def home():
    return "Welcome to the Flask API!"

# add_url_rule 함수를 사용하여 추가 URL 경로를 설정
app.add_url_rule('/api/product', 'product', get_product, methods=['GET'])
app.add_url_rule('/api/news', 'news', get_news, methods=['GET'])

# 스크립트가 직접 실행될 때만 호출되도록 지정
if __name__ == '__main__':
    app.run(debug=True)