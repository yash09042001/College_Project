/*
 Data plugin for Highcharts

 (c) 2012-2014 Torstein Honsi

 License: www.highcharts.com/license
*/
(function(j){
    var m=j.each,n=function(a,b){
        this.init(a,b)
    };
        
    j.extend(n.prototype,{
        init:function(a,b){
            this.options=a;
            this.chartOptions=b;
            this.columns=a.columns||this.rowsToColumns(a.rows)||[];
            this.columns.length?this.dataFound():(this.parseCSV(),this.parseTable(),this.parseGoogleSpreadsheet())
        },
        getColumnDistribution:function(){
            var a=this.chartOptions,b=a&&a.chart&&a.chart.type,c=[];
            m(a&&a.series||[],function(a){
                c.push((j.seriesTypes[a.type||b||"line"].prototype.pointArrayMap||[0]).length)
            });
            this.valueCount=

            {
                global:(j.seriesTypes[b||"line"].prototype.pointArrayMap||[0]).length,
                individual:c
            }
        },
        dataFound:function(){
            if(this.options.switchRowsAndColumns)this.columns=this.rowsToColumns(this.columns);
            this.parseTypes();
            this.findHeaderRow();
            this.parsed();
            this.complete()
        },
        parseCSV:function(){
            var a=this,b=this.options,c=b.csv,d=this.columns,e=b.startRow||0,h=b.endRow||Number.MAX_VALUE,i=b.startColumn||0,g=b.endColumn||Number.MAX_VALUE,f,k,o=0;
            c&&(k=c.replace(/\r\n/g,"\n").replace(/\r/g,"\n").split(b.lineDelimiter||
                "\n"),f=b.itemDelimiter||(c.indexOf("\t")!==-1?"\t":","),m(k,function(b,c){
                var k=a.trim(b),j=k.indexOf("#")===0;
                c>=e&&c<=h&&!j&&k!==""&&(k=b.split(f),m(k,function(b,a){
                    a>=i&&a<=g&&(d[a-i]||(d[a-i]=[]),d[a-i][o]=b)
                }),o+=1)
            }),this.dataFound())
        },
        parseTable:function(){
            var a=this.options,b=a.table,c=this.columns,d=a.startRow||0,e=a.endRow||Number.MAX_VALUE,h=a.startColumn||0,i=a.endColumn||Number.MAX_VALUE;
            b&&(typeof b==="string"&&(b=document.getElementById(b)),m(b.getElementsByTagName("tr"),function(a,
                b){
                b>=d&&b<=e&&m(a.children,function(a,e){
                    if((a.tagName==="TD"||a.tagName==="TH")&&e>=h&&e<=i)c[e-h]||(c[e-h]=[]),c[e-h][b-d]=a.innerHTML
                })
            }),this.dataFound())
        },
        parseGoogleSpreadsheet:function(){
            var a=this,b=this.options,c=b.googleSpreadsheetKey,d=this.columns,e=b.startRow||0,h=b.endRow||Number.MAX_VALUE,i=b.startColumn||0,g=b.endColumn||Number.MAX_VALUE,f,k;
            c&&jQuery.ajax({
                dataType:"json",
                url:"https://spreadsheets.google.com/feeds/cells/"+c+"/"+(b.googleSpreadsheetWorksheet||"od6")+"/public/values?alt=json-in-script&callback=?",
                error:b.error,
                success:function(b){
                    var b=b.feed.entry,c,j=b.length,m=0,n=0,l;
                    for(l=0;l<j;l++)c=b[l],m=Math.max(m,c.gs$cell.col),n=Math.max(n,c.gs$cell.row);
                    for(l=0;l<m;l++)if(l>=i&&l<=g)d[l-i]=[],d[l-i].length=Math.min(n,h-e);for(l=0;l<j;l++)if(c=b[l],f=c.gs$cell.row-1,k=c.gs$cell.col-1,k>=i&&k<=g&&f>=e&&f<=h)d[k-i][f-e]=c.content.$t;a.dataFound()
                }
            })
        },
        findHeaderRow:function(){
            m(this.columns,function(){});
            this.headerRow=0
        },
        trim:function(a){
            return typeof a==="string"?a.replace(/^\s+|\s+$/g,""):a
        },
        parseTypes:function(){
            for(var a=
                this.columns,b=a.length,c,d,e,h;b--;)for(c=a[b].length;c--;)d=a[b][c],e=parseFloat(d),h=this.trim(d),h==e?(a[b][c]=e,e>31536E6?a[b].isDatetime=!0:a[b].isNumeric=!0):(d=this.parseDate(d),b===0&&typeof d==="number"&&!isNaN(d)?(a[b][c]=d,a[b].isDatetime=!0):a[b][c]=h===""?null:h)
        },
        dateFormats:{
            "YYYY-mm-dd":{
                regex:"^([0-9]{4})-([0-9]{2})-([0-9]{2})$",
                parser:function(a){
                    return Date.UTC(+a[1],a[2]-1,+a[3])
                }
            }
        },
        parseDate:function(a){
            var b=this.options.parseDate,c,d,e;
            b&&(c=b(a));
            if(typeof a==="string")for(d in this.dateFormats)b=
                this.dateFormats[d],(e=a.match(b.regex))&&(c=b.parser(e));return c
        },
        rowsToColumns:function(a){
            var b,c,d,e,h;
            if(a){
                h=[];
                c=a.length;
                for(b=0;b<c;b++){
                    e=a[b].length;
                    for(d=0;d<e;d++)h[d]||(h[d]=[]),h[d][b]=a[b][d]
                }
            }
            return h
        },
        parsed:function(){
            this.options.parsed&&this.options.parsed.call(this,this.columns)
        },
        complete:function(){
            var a=this.columns,b,c,d=this.options,e,h,i,g,f,k;
            if(d.complete){
                this.getColumnDistribution();
                a.length>1&&(b=a.shift(),this.headerRow===0&&b.shift(),b.isDatetime?c="datetime":b.isNumeric||
                    (c="category"));
                for(g=0;g<a.length;g++)if(this.headerRow===0)a[g].name=a[g].shift();h=[];
                for(g=0,k=0;g<a.length;k++){
                    e=j.pick(this.valueCount.individual[k],this.valueCount.global);
                    i=[];
                    if(g+e<=a.length)for(f=0;f<a[g].length;f++)i[f]=[b[f],a[g][f]!==void 0?a[g][f]:null],e>1&&i[f].push(a[g+1][f]!==void 0?a[g+1][f]:null),e>2&&i[f].push(a[g+2][f]!==void 0?a[g+2][f]:null),e>3&&i[f].push(a[g+3][f]!==void 0?a[g+3][f]:null),e>4&&i[f].push(a[g+4][f]!==void 0?a[g+4][f]:null);
                    h[k]={
                        name:a[g].name,
                        data:i
                    };
            
                    g+=
                    e
                }
                d.complete({
                    xAxis:{
                        type:c
                    },
                    series:h
                })
            }
        }
    });
    j.Data=n;
    j.data=function(a,b){
        return new n(a,b)
    };
    
    j.wrap(j.Chart.prototype,"init",function(a,b,c){
        var d=this;
        b&&b.data?j.data(j.extend(b.data,{
            complete:function(e){
                b.hasOwnProperty("series")&&(typeof b.series==="object"?m(b.series,function(a,c){
                    b.series[c]=j.merge(a,e.series[c])
                }):delete b.series);
                b=j.merge(e,b);
                a.call(d,b,c)
            }
        }),b):a.call(d,b,c)
    })
})(Highcharts);
