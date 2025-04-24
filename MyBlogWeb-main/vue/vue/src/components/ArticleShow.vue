<template>
  <div class="article">
<!--    <div style="margin-bottom: 20px">> {{ title }}</div>-->
    <div v-html="mdText"
         style="background-color: whitesmoke;
                padding: 30px"></div>
  </div>
</template>

<script>
  const markdownIt = require('markdown-it')();

  export default{
    name: 'ArticleShow',
    data(){
      return {
        mdText: markdownIt.render('# Hello, markdown-it!'),
        title:''
      }
    },
    created() {
      this.loadMdText();
    },
    methods: {
      loadMdText(){
        const data = this.$route.query
        this.request.get(this.gotoUrl+"/file/"+data.uuid,{
          params: {
            id: data.id
          }
        }).then(res => {
          if(res.code === '1'){
            this.mdText = markdownIt.render(res.data)
          }else{
            this.$message({message: res.msg, type: 'error'})
          }
        })
      }
    },

  }
</script>

<style scope>
  .article {
    width: 650px;
    margin: auto;
  }
</style>