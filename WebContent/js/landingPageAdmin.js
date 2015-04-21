$(document).ready(function(){
  $('.sortable-list').sortable();
});

function getSem(sel) {
  var sem = document.getElementById('semester');
  sem.innerHTML = sel.value;
}