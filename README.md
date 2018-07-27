# Aloha
Aloha is time table management web application for students. Usable language is Japanese only.

## Description  

このソフトウェアはSpring Bootを利用して書かれました。フロントエンドには、CSSフレームワークとして[Honoka](https://github.com/windyakin/Honoka)を利用しています。  
後述する環境下で動作確認済です。最低限必要な機能は実装できました。  
時間割管理に役立つ学生のためのWebアプリケーションを目指して開発しています。

## Demo

<https://tomcat.mitsusawa.com/aloha/>

## Requirement

* Java 10
* Tomcat等のJava Servlet動作環境
* MariaDBまたはMySQL
* Gradleビルド環境

## Usage

ユーザ登録を行えば全ての機能が利用可能になります。

## Install

1. `./src/main/resources/application.yml`にRDBの設定をしてください。 
2. それをGradleでビルドしてください。
3. `./build`ディレクトリにwarファイルが出来上がっている事を確認してください。
4. その後、warファイルをリネーム(任意)し、デプロイしてください。(e.g. Tomcat: `webapps` フォルダ等に)
5. デフォルトの設定であれば、 `http://(FQDN)/(ファイル名)/index` にアクセスすれば使用可能です。

## Licence

[MIT](https://github.com/mitsusawa/One/blob/master/LICENSE)

## Author

[mitsusawa](https://github.com/mitsusawa)
