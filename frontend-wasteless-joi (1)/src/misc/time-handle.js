export function getTime(date){
    console.log('date is', date)
    if(date === null){
        return "-"
    }

    let dateRepr = date.toLocaleDateString();
    let split = dateRepr.split('/');
    let aux = split[0];
    split[0] = split[1];
    split[1] = aux;
    return split.join('/');
}