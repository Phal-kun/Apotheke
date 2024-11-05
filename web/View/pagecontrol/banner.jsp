<%-- 
    Document   : banner
    Created on : Nov 1, 2024, 2:39:20 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="banner.css">

    </head>
    <body>
    <div class="container-banner">
        
    <div class="box1" id="slideshow">
        <div class="slide-wrapper">
            <div class="slide"><img src="${pageContext.request.contextPath}/IMAGINE/banner/Banner_Web_PC_1610x492_11_8599674fc1.png"></div>
          <div class="slide"><img src="${pageContext.request.contextPath}/IMAGINE/banner/Banner_Web_PC_1610x492_e824ffb514.png"></div>
          <div class="slide"><img src="${pageContext.request.contextPath}/IMAGINE/banner/Decolgen_ND_1_Banner_Web_PC_1610x492_92634fc6fa.png"></div>
        </div> 
    </div>
      
    <div class="box2">
        <div class="box2-1"> <img src="${pageContext.request.contextPath}/IMAGINE/banner/Hoathuyettruongphuc_banner_homepage_PC_805x246_3f31b9c2ad.png"> </div>
        <div class="box2-2"> <img src="${pageContext.request.contextPath}/IMAGINE/banner/2_5a1afe845d.png"> </div>
    </div>
  </div>
    </body>
    <script>
    let slideIndex = 0;
        const slides = document.querySelectorAll('#slideshow .slide');
      
        function showSlides() {
          slides.forEach(slide => slide.style.display = 'none');
          slideIndex++;
          if (slideIndex > slides.length) {
            slideIndex = 1;
          }
          slides[slideIndex - 1].style.display = 'block';
          setTimeout(showSlides, 5000); // Chuyển đổi sau mỗi 7 giây
        }
      
        // Bắt đầu slideshow
        showSlides(); 
</script>
</html>
