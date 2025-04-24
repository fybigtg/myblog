<template>
  <div>
    <el-table
        @row-click="rowClick"
        :data="tableData"
        stripe
        style="width: 100%">
      <el-table-column
          prop="filename"
          label="标题">
      </el-table-column>
      <el-table-column
          prop="date"
          label="发布日期"
          width="300">
      </el-table-column>
      <el-table-column
          :filterclick="1"
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-button type="danger" plain @click="checkDetail(scope.row)">删除</el-button>
        </template>

      </el-table-column>
    </el-table>

    <div class="block" style="margin-top: 10px">
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          layout="prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  export default{
    name: 'MyArticle',
    data(){
      return{
        tableData: [],
        total: 10,
        pageNum: 1,
        pageSize: 10,
        currid: 0
      }
    },
    created(){
      this.currid = JSON.parse(localStorage.getItem("accountUser")).id
      this.loadTable()
    },
    mounted(){
      this.loadTable()
    },
    methods:{
      checkDetail(row){
        this.request.delete(this.gotoUrl+"/file/"+row.id,{
          params:{
            currid: this.currid
          }
        }).then(res => {
          if(res.code === '1') {
            this.loadTable()
          }else {
            this.$message({message: '操作失败', type: 'error'})
          }
        })
      },
      rowClick(row,event,colum){
        if(event.label !== "操作"){
          this.showArticle(row.id,row.uuid)
        }
        //this.showArticle(row.id,row.uuid)
      },
      handleCurrentChange(val){
        this.pageNum=val
      },
      loadTable(){
        this.request.get(this.gotoUrl+"/file/currpage",{
          params:{
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            currid: this.currid
          }
        }).then(res => {
          if(res.code === '1') {
            this.total = res.data.total
            this.tableData = res.data.pageList
          }else {
            this.$message({message: '数据获取失败', type: 'error'})
          }
        })
      },
      showArticle(id,uuid){
        this.$router.push({
          path:'/manage/articleshow',
          query:{
            id: id,
            uuid: uuid
          }
        })
      },
    }
  }
</script>