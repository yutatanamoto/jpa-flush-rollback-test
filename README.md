# saveAndFlush の挙動確認用サンプルプロジェクト

# 確認手順
1. MySQL のコンテナを起動
```aidl
$ docker ompose up -d
```
2. アプリケーションを起動
3. saveAndFlush 呼び出し＆非検査例外送出
```aidl
$ curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Daniel", "lastName": "Caesar"}' localhost:8080/users
```
4. ロールバックされていることを確認
```aidl
$ docker exec mysql-container mysql -uuser -ppassword -P3306 sample -e "select count(*) from users;"
```