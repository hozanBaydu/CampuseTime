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

![giriş sayfası](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjeSb90-va32n8ZAQzl3yfFTyxVU15zjYdHDRLy-u-qWR1ID2T8fGU8jlIqzwcF4554uWIeydM-t3_vjfTwJf0Z8SBRiXQW8DINmRtcG3kGmRqQPLjxi6OILv5ImoBhEzQVnczyMp9TRPI2ubzsK3-sGueXZm5BmqLruPpGp6tEN77HnJnyzfK5YRAY/s600/image3.jpeg)

Her okul için farklı bir recyclerview olacağından bir menu oluşturdum.Recyclerview tıklanan okula göre güncellenecektir.

```sh
 private fun getData(uni:String){


        db.collection(uni).orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error!=null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()

            }else{
                if (value!=null){
                    if(!value.isEmpty){

                        val documents=value.documents
                        
                        postArrayList.clear()
                        
                        for (document in documents){
                            
                            val comment=document.get("comment") as String
                            val userEmail=document.get("userEmail") as String
                            val dowloadUrl=document.get("dowlandUrl") as String
                            val post=Post(userEmail,comment,dowloadUrl)
                            postArrayList.add(post)
                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
```

Her okul için farklı bir bilgi çekme fonksiyonu yazmanın verimsiz olacağını düşünüp tek bir veri çekme fonksiyonu yazdım.Her okul için yazılan fonksiyonda sadece getData fonksiyonunu çağırıp istenen uni:String değerinin verilmesi yeterli olacaktır.Çünkü bir sonraki fotoğraf yükleme sayfasında açıklayacağım üzere firebaseye fötoğrafları gönedrirken hangi okul için olduğu bilgisi verilmektedir.



```sh
if (item.itemId==R.id.odtu){
            getData("odtu")
            binding.recyclerView.layoutManager=LinearLayoutManager(this)
            feedAdapter= FeedRecyclerAdapter(postArrayList)
            binding.recyclerView.adapter=feedAdapter
```

Yukarıda örnek olarak odtu için çağırma fonksiyonu gösterilmiştir.Getdata fonksiyonunda istenen uni değeri odtu olacağından  db.collection(uni).orderBy("date",Query.Direction.DESCENDING).addSnapshotListener--
kodu odtu koleksiyonundaki fotoğrafları çekecektir.
```sh
if (item.itemId==R.id.odtu){
            getSupportActionBar()?.setTitle("Orta Doğu Teknik Üniversitesi")
            getSupportActionBar()?.setBackgroundDrawable( ColorDrawable(Color.parseColor("#1c6071")))
```
Action Barın seçilen okula göre renk ve isim değiştirilmesi için yukarıdaki kodları yazdım.

## Yükleme sayfası

Kullanıcılar bu sayfadan gönderi ekleyebilecektir.
