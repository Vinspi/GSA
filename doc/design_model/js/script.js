

var globalOne

function onMouseEnterPanel(){
  globalOne = setTimeout(function() {
    $('#panel span').show();
    $('#panel span').css('opacity','1');
  }, 300);

}

function onMouseLeavePanel(){
  clearTimeout(globalOne);
  $('#panel span').hide();
  $('#panel span').css('opacity', '0');
}
