<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Elitewear - Products</title>
  <link rel="stylesheet" href="css/main.css">  
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

  <!-- Header -->
  <div class="header">
    <div class="container">
      <div class="navbar">
        <div class="logo">
          <a href="index.jsp"><img src="images/Elite.logo.png" width="140px" alt="logo"></a>
        </div>
        <nav>
          <ul id="MenuItems">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="product.jsp">Products</a></li>
            <li><a href="aboutus.jsp">About</a></li>
            <li><a href="contactus.jsp">Contact</a></li>
            <li><a href="account.jsp">Account</a></li>
          </ul>
        </nav>
        <a href="cart.jsp"><img src="images/cart.png" width="30px" height="30px" alt="Cart"></a>
        <img src="images/menu.png" class="menu-icon" alt="Menu" onclick="menutoggle()">
      </div>
    </div>
  </div>

  <!-- Products Section -->
  <div class="small-container">
    <div class="row row-2">
      <h2>All Products</h2>
      <select>
        <option>Default Sorting</option>
        <option>Sort By Price</option>
        <option>Sort By Popularity</option>
        <option>Sort By Rating</option>
        <option>Sort By Sale</option>
      </select>
    </div>

    <div class="row">
      <!-- Product Items -->
      <div class="col-4">
        <a href="productdetails.jsp"><img src="images/product-1.jpg" alt="Red T-Shirt"></a>
        <a href="productdetails.jsp"><h4>Red T-Shirt</h4></a>
        <div class="rating">
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star-o"></i>
        </div>
        <p>Rs 500</p>
      </div>

      <div class="col-4">
        <a href="productdetails.jsp"><img src="images/product-2.jpg" alt="Shoes"></a>
        <a href="productdetails.jsp"><h4>Shoes</h4></a>
        <div class="rating">
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star-o"></i>
        </div>
        <p>Rs 800</p>
      </div>

      <div class="col-4">
        <a href="productdetails.jsp"><img src="images/product-3.jpg" alt="Pants"></a>
        <a href="productdetails.jsp"><h4>Pants</h4></a>
        <div class="rating">
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star-o"></i>
        </div>
        <p>Rs 1000</p>
      </div>

      <div class="col-4">
        <a href="productdetails.jsp"><img src="images/product-4.jpg" alt="Shoes"></a>
        <a href="productdetails.jsp"><h4>Shoes</h4></a>
        <div class="rating">
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star-o"></i>
        </div>
        <p>Rs 1500</p>
      </div>

      <div class="col-4">
        <a href="productdetails.jsp"><img src="images/product-5.jpg" alt="Shocks"></a>
        <a href="productdetails.jsp"><h4>Shocks</h4></a>
        <div class="rating">
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star-o"></i>
        </div>
        <p>Rs 200</p>
      </div>

      <div class="col-4">
        <a href="productdetails.jsp"><img src="images/product-6.jpg" alt="Watch"></a>
        <a href="productdetails.jsp"><h4>Watch</h4></a>
        <div class="rating">
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star"></i><i class="fa fa-star"></i>
          <i class="fa fa-star-o"></i>
        </div>
        <p>Rs 1200</p>
      </div>

      <!-- Add other products in same way -->
    </div>

    <!-- Pagination -->
    <div class="page-btn">
      <span>1</span>
      <span>2</span>
      <span>3</span>
      <span>4</span>
      <span>&#8594;</span>
    </div>
  </div>

  <!-- Footer -->
  <div class="footer">
    <div class="container">
      <div class="row">
        <div class="footer-col-1">
          <h3>Download Our App</h3>
          <p>Download the app for Android and iOS mobile phones.</p>
          <div class="app-logo">
            <img src="images/play-store.png" alt="Play Store">
            <img src="images/app-store.png" alt="App Store">
          </div>
        </div>

        <div class="footer-col-2">
          <img src="images/Elitewear-3.png" alt="Elitewear Logo">
        </div>

        <div class="footer-col-3">
          <h3>Useful Links</h3>
          <ul>
            <li>Coupons</li>
            <li>Blog Post</li>
            <li>Return Policy</li>
            <li>Join Affiliate</li>
          </ul>
        </div>

        <div class="footer-col-4">
          <h3>Follow Us</h3>
          <ul>
            <li>Facebook</li>
            <li>Twitter</li>
            <li>Instagram</li>
            <li>YouTube</li>
          </ul>
        </div>
      </div>
      <hr>
      <p class="Copyright">Copyright 2025 - EliteWear</p>
    </div>
  </div>

  <!-- JS for toggle menu -->
  <script>
    var MenuItems = document.getElementById("MenuItems");
    MenuItems.style.maxHeight = "0px";

    function menutoggle() {
      if (MenuItems.style.maxHeight === "0px") {
        MenuItems.style.maxHeight = "200px";
      } else {
        MenuItems.style.maxHeight = "0px";
      }
    }
  </script>

</body>
</html>
