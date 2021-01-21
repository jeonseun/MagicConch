function clickLike(click){
    if(click == 'like'){
        $('#like').hide();
        $('#no-like').show();
    }
    else if(click == 'noLike'){
        $('#like').show();
        $('#no-like').hide();
    }
}