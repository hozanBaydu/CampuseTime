# CampuseTime
## Hozan BAYDU

Merhaba,ben Hozan.Bu uygulamayı Firebase ve kotlin kullanarak yazdım.
Bu README dosyasında kısaca,yaptığım uygulamanın özelliklerini anlatıp bu özellikleri nasıl kodladığımı anlatacağım.  

 1. Uygulama tanıtımı
 2.Kodlar

## Özellikler

- Her kullanıcı istediği bir okul için paylaşım yapabilecek.
- Kullanıcılar mail ile kayıt yapabilecek.
- Kullanıcı isteğine göre birden fazla okul için paylaşım yapılabilecektir.


> Bu uygulama kullanıcılar için bir sosyal medya 
> olmanın ötesinde hem öğrencilerin kendi
> okul çevrelerinde sosyalleşmesi hem de 
> sınav soruları gibi ders araçlarının paylaşıldığı bir  
> platform olması amacıyla yazılmıştır.


## Giriş sayfası

Bu sayfada kullanıcılar uygulamaya kayıt yaptırabilir veya uygulamadan çıkış yapılmış ise
bu sayfadan giriş yapılır.

![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgrkxu_FHkk8EUB9RfqZCGwH-JsPZmRBd2BToeIBNbSWY9ZdmOEAlZCvycG40u_Qe5gOPVkdFjKeKvO7H7j7tIPqjDU_voPcNWkELxXw0_AhgwY7WAY5Ibw_jax-l-QZdDS0veVHUAfOvoo8hXLciFmA3FFntNoWePm2L3VRQsRycZA9YUc0UCZb92_/s600/image1.jpeg)

Öncelikle onCreate altında action barı kapatarak aktiviteyi yazmaya başladım.Uygulamanın tamamında kapatmak istemediğimden dolayı aktivitenin içinde kapattım.Bir sonraki aktivitede ihtiyacımız olacaktır.

```sh
val currentUsers=auth.currentUser  //initialize ettik.

        if (currentUsers!=null){
            val intent=Intent(this@MainActivity, FeedActivity::class.java)   
            startActivity(intent)
            finish()
        }
```

Kullanıcı uygulamaya her giriş yapmak istendiğinde bu sayfanın çıkası ve kullanıcı bilgilerini sorması kullanışlı olmayacağından dolayı eğer mevcut bir kullanıcı girişi daha önce yapılmış ve çıkış işlemi gerçekleşmemiş ise doğrudan uygulamanın içine geçiş yapılması için yukarıdaki kodu yazdım.Kullanıcı giriş yaptıktan sonra çıkış yapmak için sonraki aktivitede çıkış yap butonuna tıklayarak çıkış yapabilmektedir.Geri tuşuna basarak çıkış yapmaması için aktiviteyi bitirmek için finish() kodu ekledim.


## Ana sayfa
Kullanıcının paylaşımları görebileceği sayfadır.

![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhpkjMYPOQM-QB19pO5ycirDY1c4gn7352l_JUsJgbbrgq5HVT2B9AXNFV9XQHfN7vmeB8arSddA7HKePszZv0nQ6X6CMIz63yMStjWAKP8RxaMsrJMtpbgKQVAxC6CdhgiEsRIoRwMhngTtAzf4fTz7J6HLa17jBbrEoziglgw3DaIQc_TbIO2mZdp/s600/image2.jpeg)


Bu sayfada tüm gönderilerin aynı anda okunmaması için recyclerview kullandım.
Bunun için viewbindingten yararlanarak adapter kodladım.Bu adapterde bir postList:ArrayList<Post>
isteniyor ve bu değeri firebaseden çekerek recyclerviewde gösteriyor.
