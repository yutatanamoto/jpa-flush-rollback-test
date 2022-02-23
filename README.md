# saveAndFlush の挙動確認用サンプルプロジェクト
JPA の `saveAndFlush` がちゃんとロールバックされるか不安になってしまった人（自分）のための動作確認用プロジェクト
# 概要
簡単なユーザー登録アプリケーションを例に、`saveAndFlush` がロールバックされることを確認します。
今回の動作確認で重要なのは `UserService.java` の `add` メソッドです。
```java
@Transactional
public void add(String firstName, String lastName) {
    User user = new User(null, firstName, lastName);
    userRepository.saveAndFlush(user);
    // ここで非検査例外発生
    throw new RuntimeException();
}
```
上記の `add` メソッドを実行後、DB の内容がどうなるか確認します。
# 動作確認手順
### 1. MySQL のコンテナ起動 & テーブル作成
以下のコマンドを実行すると MySQL のコンテナが起動します。  
また、users テーブルが作成されます。
```bash
$ docker compose up -d
```
### 2. アプリケーション起動
### 3. saveAndFlush 呼び出し＆非検査例外送出
```bash
$ curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Daniel", "lastName": "Caesar"}' localhost:8080/users
```
### 4. saveAndFlush による変更がロールバックされていることを確認
以下のコマンドを実行することで現在 `users` テーブルに永続化されているレコード数を出力できます。  
ロールバックされているのであれば、この出力が 0 になります。  
実際に実行すると 0 が出力されるはずです。
```bash
$ docker exec mysql-container mysql -uuser -ppassword -P3306 sample -e "select count(*) from users;"
```

# おまけ
そもそもユーザー登録の実装が間違っていて、ロールバック云々以前にユーザーが登録できないようになっているのでは、と思うこともあるかと思います。  
以下を行うことでユーザー登録の実装自体に問題がないことを確認できます。  
### 1. MySQL のコンテナを起動 & テーブル作成
```bash
$ docker compose up -d
```
### 2. `UserService#add` 内の例外送出処理をコメントアウト
`throw new RuntimeException();` の一行をコメントアウトすれば良いです。  
これでユーザー登録が行えるようになります。  
### 3. アプリケーションを起動
### 4. ユーザー登録のリクエストを送信
```bash
$ curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Daniel", "lastName": "Caesar"}' localhost:8080/users
```
### 5. 3 のリクエストに対応するレコードが `users` テーブルに存在することを確認
以下のコマンドを実行することで現在 `users` テーブルに永続化されているレコードを出力できます。  
```bash
$ docker exec mysql-container mysql -uuser -ppassword -P3306 sample -e "select * from users;"
```