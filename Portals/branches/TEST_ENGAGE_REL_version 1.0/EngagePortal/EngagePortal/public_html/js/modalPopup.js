function enforcePreventUserInput(evt){
//alert('hai');
var popup = AdfPage.PAGE.findComponentByAbsoluteId('pt1:p12');
//alert("hai2"+popup);
if (popup != null){
AdfPage.PAGE.addBusyStateListener(popup,handleBusyState);
evt.preventUserInput();
}
}
//JavaScript call back handler
function handleBusyState(evt){
//alert('inside handle busy');
var popup = AdfPage.PAGE.findComponentByAbsoluteId('pt1:p12');
if(popup!=null){
if (evt.isBusy()){
popup.show();
}
else
if (popup.isPopupVisible())
{
popup.hide();
AdfPage.PAGE.removeBusyStateListener(popup,handleBusyState);
}
}
}


function searching(evt){
//alert('hai');
var popup = AdfPage.PAGE.findComponentByAbsoluteId('pt1:p13');
//alert("hai2"+popup);
if (popup != null){
AdfPage.PAGE.addBusyStateListener(popup,handleBusyStateSearching);
evt.preventUserInput();
}
}
function handleBusyStateSearching(evt){
//alert('inside handle busy');
var popup = AdfPage.PAGE.findComponentByAbsoluteId('pt1:p13');
if(popup!=null){
if (evt.isBusy()){
popup.show();
}
else
if (popup.isPopupVisible())
{
popup.hide();
AdfPage.PAGE.removeBusyStateListener(popup,handleBusyStateSearching);
}
}
}
function validate(evt){
//alert('hai');
var popup = AdfPage.PAGE.findComponentByAbsoluteId('pt1:validate');
//alert("hai2"+popup);
if (popup != null){
AdfPage.PAGE.addBusyStateListener(popup,handleBusyStateValidate);
evt.preventUserInput();
}
}
function handleBusyStateValidate(evt){
//alert('inside handle busy');
var popup = AdfPage.PAGE.findComponentByAbsoluteId('pt1:validate');
if(popup!=null){
if (evt.isBusy()){
popup.show();
}
else
if (popup.isPopupVisible())
{
popup.hide();
AdfPage.PAGE.removeBusyStateListener(popup,handleBusyStateValidate);
}
}
}
function save(evt){
//alert('hai');
var popup = AdfPage.PAGE.findComponentByAbsoluteId('pt1:save');
//alert("hai2"+popup);
if (popup != null){
AdfPage.PAGE.addBusyStateListener(popup,handleBusyStateSave);
evt.preventUserInput();
}
}
function handleBusyStateSave(evt){
//alert('inside handle busy');
var popup = AdfPage.PAGE.findComponentByAbsoluteId('pt1:save');
if(popup!=null){
if (evt.isBusy()){
popup.show();
}
else
if (popup.isPopupVisible())
{
popup.hide();
AdfPage.PAGE.removeBusyStateListener(popup,handleBusyStateSave);
}
}
}

function printDivSaldo(div1,div2) {
         
     var printContents = document.getElementById(div1).innerHTML+document.getElementById(div2).innerHTML;
     
     var originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;

     location.reload();
}     


 function printDiv(div1,div2,div3) {
         
     var printContents = document.getElementById(div1).innerHTML+document.getElementById(div2).innerHTML+document.getElementById(div3).innerHTML;
     
     var originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;

     location.reload();
}     

 function doPrintPage(){
     var printContents = document.getElementById('pt1:r1:0:printPanel1').innerHTML+document.getElementById('pt1:r1:0:printPanel2').innerHTML;
     
     var originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;
     location.reload();
     
   } 

