// Swiper slide desk
const swiper = new Swiper(".mySwiper", {
  loop: true,
  spaceBetween: 10,
  slidesPerView: 8,
  freeMode: true,
  watchSlidesVisibility: true,
  watchSlidesProgress: true,
});
const swiper2 = new Swiper(".mySwiper2", {
  loop: true,
  spaceBetween: 10,
  navigation: {
    nextEl: ".swiper-button-next--desktop",
    prevEl: ".swiper-button-prev--desktop",
  },
  thumbs: {
    swiper: swiper,
  },
});
// Swiper slide mobile
const swiperMobile = new Swiper(".mySwiper--mobile", {
  loop: true,
  spaceBetween: 10,
  slidesPerView: 8,
  freeMode: true,
  watchSlidesVisibility: true,
  watchSlidesProgress: true,
});
const swiper2Mobile = new Swiper(".mySwiper2--mobile", {
  loop: true,
  spaceBetween: 10,
  navigation: {
    nextEl: ".swiper-button-next--mobile",
    prevEl: ".swiper-button-prev--mobile",
  },
  thumbs: {
    swiper: swiperMobile,
  },
});
//-----------------------
