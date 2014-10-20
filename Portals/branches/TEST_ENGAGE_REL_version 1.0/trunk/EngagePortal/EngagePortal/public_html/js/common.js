
var Common = {

	params: {},
    
    init:function(params){
        var self = this;
        alert("Hello");
    
 self.initHomeSlideshow(
            $(".__jsHomeSlideshowMask"),
            $(".__jsHomeSlideshowItems"),
            $(".__jsHomeSlideshowControls"),
            $(".__jsHomeSlideshowBubbles"),
            {
                delaySpeed: 8000,   /* @param milisec Timeout between slides */
                slideSpeed: 500,    /* @param milisec Slides speed */
                showFirst:  0,      /* @param int Which one element show as first, begining from 0  */
                autoPlay:   true    /* @param boolean False if disabled auto animation */
            }
        );
 
    },

    
    initChk: function($container,params){
        var self = this;
        var defaults = {
            activeClass:    'sel',
            labelElement:   'label',
            triggerElement: 'a'
        };

    },

  
    /**
     * Home slideshow
     */ 
    
    initHomeSlideshow: function($mask, $items, $controls, $bubbles, params){
        var self = this;
        var defaults = {
            delaySpeed: 5000,
            slideSpeed: 500,
            showFirst:  0,
            itemClass:  'item',
            autoPlay:   true
        };                                 
        params = $.extend(defaults, params);
        
        if(!$mask.length || !$items.length)
            return false;
        
        var counter     = params.showFirst;
        var $item       = $("."+params.itemClass,$items);
        var itemsCount  = $item.length - 1;
        var itemWidth   = $item.outerWidth(true);
        var itemsWidth = itemWidth * (itemsCount + 1);
        var timer       = null;
        var $bubble     = null;
        
        var $firstItem = $item.eq(0);
        var $lastItem = $item.eq(itemsCount);    
//clearing the timer
		var clearTimer = function() {
			clearTimeout(timer);
			timer = null;
		};
		//on clicking of refresh call clearTimeout
        var inittimer   = function(refresh){            
            if(refresh)
            clearTimeout(timer);
           //for autoscrolling 
			var autoAdvance = function () {
				if(timer) {
					var current = counter;
					counter = (counter == itemsCount) // if counter is the last item again set it to 0 or else add 1 to the current count
						? 0
						: counter + 1
					;

					self.__scrollToItem($mask, $bubble, $controls, $item, counter, current, params.slideSpeed, 'forward'); // scroll in the forward direction

					timer = setTimeout(autoAdvance, params.delaySpeed); //recurring call to autoadvance with the time
				}
			};

			timer = setTimeout(autoAdvance, params.delaySpeed); // when timer is null
		};
// for 3 bubbles displayed down
        var initbubbles = function(){ //initialising bubbles
            
            var htmlData = '';
            
            
            $bubbles.append(htmlData);
            $bubble = $("a",$bubbles);
            
            var width = parseInt($bubbles.outerWidth());
            
            $bubbles.css({"width": width+"px", "marginLeft":"-"+(width/2)+"px"});  
                
        }
        
        if(itemsCount <= 0){ //if no item is present
            $controls.hide();
            return false;
        }
        
        initbubbles();
        
        $items.css({width:itemsWidth});
		self.__scrollToItem($mask, $bubble, $controls, $item, counter, 0, 0);

        $bubble.click(function(){ //on clicking the bubble
            var $e      = $(this);
            var index   = $bubble.index($e); //get index of the bubble clicked
            
			var current = counter; 
            counter = index;
            
			if (params.autoPlay) { //for autoplay
                inittimer(true);
			}
            
			self.__scrollToItem($mask, $bubble, $controls, $item, counter, current, params.slideSpeed, 'auto'); 

            return false;
        });
        
        $controls.click(function(){
            var $e = $(this);
            var direction = ($e.hasClass('prev')) ? -1 : 1;  //if prev is clicked move backwards
            
			if ($e.hasClass("disabled") == true) {
                return false;
			}

			var current = counter;
            
            if(direction == -1){ //move backwards
                counter = (counter == 0) 
                    ? itemsCount 
					: counter - 1
				;
            }
            else{ //move forward
                counter = (counter == itemsCount) 
                    ? 0 
					: counter + 1
				;
            }

			if (params.autoPlay) { //autoplay
				inittimer(true);
			}
            
			self.__scrollToItem($mask, $bubble, $controls, $item, counter, current, params.slideSpeed, direction == -1 ? 'backward' : 'forward');
     
            return false;
		});
        
		if (params.autoPlay) {
            inittimer();   
		}
    },
    
	__scrollToItem:function ($mask, $bubble, $controls, $items, targetIndex, currentIndex, speed, direction) {
		var self = this;
		var itemCount = $items.length;
		var lastIndex = itemCount - 1;
		var $selItem = $items.eq(targetIndex);
		var itemWidth = $selItem.outerWidth(true);
		var $selBubble = $bubble.eq(targetIndex);
		var sliderWidth = (itemCount * itemWidth);
        
		var startProcess = function() {
        	$bubble.removeClass("sel");
        	$selBubble.addClass("sel");
        	$controls.addClass("disabled");
		};
        
		var endProcess = function() {
					$items.removeClass("sel");
					$selItem.addClass("sel");
					$controls.removeClass("disabled");
		};

		var delta = Math.abs(targetIndex - currentIndex);

		var $masks = $mask.children();

		var wrapAround = direction != 'auto';

		startProcess();

		if (wrapAround && currentIndex == 0 && targetIndex == lastIndex) {
			//wraparound going backward

			$masks.css({
				left: 0
			});

			$selItem.css({
				position: 'absolute',
				left: -itemWidth + 'px'
			});

			$masks.animate({
				left: itemWidth + 'px'
			}, speed, function() {
				$selItem.css({
					position: 'relative',
					right: 'auto',
					left: 'auto'
				});
				$masks.css({
					left: -(itemCount - 1) * itemWidth + 'px'
				});
				endProcess();
			});

		} else if (wrapAround && currentIndex == lastIndex && targetIndex == 0) {
			// wraparound going forward

			$masks.css({
				left: -(itemCount - 2) * itemWidth + 'px'
			});

			$selItem.css({
				position: 'absolute',
				right: 0
			});

			$masks.animate({
				left: -(itemCount - 1) * itemWidth + 'px'
			}, speed, function() {
				$selItem.css({
					position: 'relative',
					right: 'auto',
					left: 'auto'
				});
				$masks.css({
					left: 0
				});
				endProcess();
			});

		} else {
			// no wraparound

			$masks.animate({
				left: -targetIndex * itemWidth + 'px'
			}, speed, function() {
				endProcess();
			});
		}

    },

	initInputPlaceholders: function() {
		$(function() {
			$('input[placeholder], textarea[placeholder]').placeholder();
		});
	},

};

