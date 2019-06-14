$(function() {
  // ［検索］ボタンクリックで検索開始
  $('#search').click(function() {
	 console.log($('#zipCode1').val());
    $.ajax({
        url: "http://zipcoda.net/api",
        dataType: "jsonp",
        data: { 
        　
          zipcode: $('#zipCode1').val() + $('#zipCode2').val() 
        },
      }
    )
    // 検索成功時にはページに結果を反映
    .done(function(data) {
      // コンソールに取得データを表示
      console.log(data);
      $("#address").val(data.items[0].address);
    })
    // 検索失敗時には、その旨をダイアログ表示
    .fail(function() {
      window.alert('正しい結果を得られませんでした。');
    });
  });
});