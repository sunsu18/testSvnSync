function refreshCarousal(){ 
function mycarousel_initCallback(carousel)
{
    // Disable autoscrolling if the user clicks the prev or next button.
    carousel.buttonNext.bind('click', function() {
        carousel.startAuto(0);
    });
 
    carousel.buttonPrev.bind('click', function() {
        carousel.startAuto(0);
    });
 
    // Pause autoscrolling if the user moves with the cursor over the clip.
    carousel.clip.hover(function() {
        carousel.stopAuto();
    }, function() {
        carousel.startAuto();
    });
};
 
jQuery(document).ready(function() {
    jQuery('#mycarouselunauth').jcarousel({
        auto: 10,
        wrap: 'last',
      
        initCallback: mycarousel_initCallback
    });
    jQuery('#mycarouselauth').jcarousel({
        auto: 10,
        wrap: 'last',
      
        initCallback: mycarousel_initCallback
    });
    
});
}