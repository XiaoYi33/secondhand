<template>
  <div>



    <el-row>
      <el-col></el-col>
      <el-button>nihao </el-button>
      <el-button type="primary">蓝色 </el-button>

      <el-button type="primary" circle plain icon="el-icon-edit"></el-button>
    </el-row>

    <el-row>
      <el-col>
        <el-input style="width: 200px" v-model="value" placeholder="请输入内容"></el-input>
        <el-input type="textarea" style="width: 200px" v-model="value" ></el-input>
        <el-input show-password style="width: 200px;" v-model="value" placeholder="请输入密码"></el-input>
      </el-col>
    </el-row>

    <el-row style="margin: 20px 0">
      <el-autocomplete placeholder="请输入内容" :fetch-suggestions="query" :trigger-on-focus="false" v-model="value"></el-autocomplete>
    </el-row>

    <el-row :gutter="10">
      <el-select v-model="select" @change="changeSelect">
        <el-option value="香蕉" ></el-option>
        <el-option value="苹果"></el-option>
        <el-option value="榴莲"></el-option>
      </el-select>

      <el-select v-model="select" @change="changeSelect">
        <el-option v-for="item in fruits" :key="item.id"  :value="item.name" ></el-option>
      </el-select>
    </el-row>

    <el-row style="margin: 20px 0">
      <el-table :data="tableData" >
        <el-table-column label="序号" prop="id"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
          </template>
        </el-table-column>

      </el-table>

    </el-row>



  </div>
</template>
<script>
export default {
  data() {
    return {
      value: '',
      coffees:[{value:'1星巴克'},{value: '2麦咖啡'},{value: '3瑞幸'}],
      select:'',
      fruits:[
        {name:'苹果',id:1},
        {name:'香蕉',id:2},
        {name:'榴莲',id:3}
      ],
      tableData:[
        {name:'test',id:1,age:18}
      ]
    }
  },
  methods:{
    query(query,cb){
      let result=query ? this.coffees.filter(v => v.value.includes(query)) : this.coffees
      console.log(result)
      cb(result);
    },
    changeSelect(){
      console.log(this.select)
    },
    edit(row){
      alert(row.name)
    }

  }
}
</script>