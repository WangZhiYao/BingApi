# Bing

A restful bing api

**Currently only supported in Chinese**. I will be very glad if you can provide the data in other languages.

Get new data at 00:00:05 (UTC+8) every day

## 1.How to use
1. Get a random image:
```url
GET https://api.zephaniel.site/bing/v1/random
```

2. Get an image by date
```url
GET https://api.zephaniel.site/bing/v1/getByDate?date={yyyyMMdd}
```

## 2.Sample Response
There is no description & location anymore after 2019.02.28
```json
{
  "data": {
    "year": 2018,
    "month": 11,
    "day": 26,
    "copyright": "纽约中央公园内的爱丽丝梦游仙境雕塑，美国纽约 (© Diego Grandi/Shutterstock)",
    "description": "这座爱丽丝梦游仙境雕塑位于纽约中央公园东侧，靠近75街。它是1959年由一位慈善家委托制作的，他已故的妻子喜欢给孩子们阅读《爱丽丝梦游仙境》。这本儿童读物于1865年的这一天首次出版，作者是英国作家查尔斯·路特维奇·道奇森，他的笔名是刘易斯·卡罗尔。他的故事讲述了一个掉进兔子洞进入一个奇幻世界的小女孩，故事的灵感来自一个真实的女孩爱丽丝·李道尔，这个故事后来成为了经典，并对后世文学、电影创作产生了极大的影响。",
    "location": "北美洲, 美国, 纽约",
    "image": "https://bing.339.im/images/AliceCentralPark_EN-AU9031006021_1920x1080.jpg"
  },
  "code": 1000
}
```
