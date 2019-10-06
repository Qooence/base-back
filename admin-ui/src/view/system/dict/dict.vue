<template>
  <div>
    <Card class="card-content">
      <p slot="title"
          class="card-content-title">
          <Row type="flex" justify="start" class="card-content-row">
            <Col span="12" class="card-content-add"  v-hasPermission="hasPermissions.add"><img @click="goToAdd()" src="@/assets/images/icon-add.png"></Col>
            <Col span="12" v-hasPermission="hasPermissions.delete"><img @click="confDel" src="@/assets/images/icon-delete.png"></Col>
        </Row>
      </p>
      <tree-table
        ref="treeTable"
        :data="data"
        :columns="columns"
        :border="true"
        :expand-type="false"
        :selectable="true">
        <template slot="operation" slot-scope="scope">
          <Button v-hasPermission="hasPermissions.update" type="warning" @click="goToAdd(scope.row.id)">编辑</Button>
        </template>
      </tree-table>
      <dict-add-update
        v-model="addUpdate"
        :title="addUpdateTitle"
        :treeData="data"
        ref="dictAddUpdate"
        @refreshDataList="initData">
      </dict-add-update>
    </Card>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import dictAddUpdate from './dict-add-update'
export default {
  data () {
    return {
      addUpdate: false,
      addUpdateTitle: '新增',
      data: [],
      hasPermissions: {
        add: ['sys:dict:save'],
        update: ['sys:dict:update', 'sys:dict:info'],
        delete: ['sys:dict:delete']
      },
      columns: [
        {
          title: '名称',
          key: 'name',
          width: '200px',
          headerAlign: 'center'
        },
        {
          title: '编码',
          key: 'code',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center'
        },
        {
          title: '描述',
          key: 'description',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center'
        },
        {
          title: '排序',
          key: 'seq',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center'
        },
        {
          title: '操作',
          minWidth: '20px',
          align: 'center',
          headerAlign: 'center',
          type: 'template',
          template: 'operation'
        }
      ]
    }
  },
  components: {
    dictAddUpdate
  },
  methods: {
    ...mapActions(['getDictTreeList', 'deleteDicts']),
    initData () {
      this.getDictTreeList().then(res => {
        this.data = res.data.tree
      }).catch(err => {
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    },
    goToAdd (id) {
      this.addUpdate = true
      this.addUpdateTitle = id ? '编辑' : '新增'
      this.$nextTick(() => {
        this.$refs.dictAddUpdate.init(id)
      })
    },
    confDel () {
      let deletes = this.$refs.treeTable.getCheckedProp('id')
      if (deletes.length > 0) {
        this.$Modal.confirm({
          title: '提示',
          content: '确认进行删除操作吗？',
          onOk: () => {
            this.deleteDicts({ dictIds: deletes }).then(() => {
              this.initData()
              this.$Notice.success({
                title: '提示',
                desc: '删除成功'
              })
            }).catch(err => {
              this.$Notice.error({
                title: '错误',
                desc: err.msg
              })
            })
          },
          onCancel: () => {
            this.initData()
          }
        })
      } else {
        this.$Notice.warning({
          title: '警告',
          desc: '请至少选择一项'
        })
      }
    }
  },
  mounted () {
    this.initData()
  }
}
</script>
<style lang="less">
  @import "~@/style/style.less";
</style>
