<template>
    <div>
      <Card class="card-search">
        <Form :model="searchParam" :label-width="80">
          <Row>
            <Col span="21">
              <FormItem label="报警时间">
                <DatePicker type="daterange"
                  split-panels
                  placement="bottom-start"
                  placeholder="时间范围"
                  style="width: 200px"
                  @on-change="timeConfir">
                </DatePicker>
              </FormItem>
            </Col>
            <Col span="3" class=" card-search-search-btn-box" :label-width="10">
              <Button type="primary" @click="search">搜索</Button>
            </Col>
          </Row>
        </Form>
      </Card>
      <Card class="card-content">
        <div class="card-content-table">
          <Table border
            ref="selection"
            :loading="loading"
            :columns="colData"
            :data="listData">
          </Table>
        </div>
        <div class="card-content-pages">
          <Page :total="totalCount"
            show-elevator
            show-total
            :page-size="searchParam.limit"
            @on-change='change'
            @on-page-size-change='change'>
          </Page>
        </div>
      </Card>
    </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      searchParam: {
        page: 1,
        limit: 10
      },
      loading: true,
      colData: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '设备型号',
          key: 'devType',
          tooltip: true,
          align: 'center'
        },
        {
          title: '配件编号',
          key: 'sensorNum',
          tooltip: true,
          align: 'center'
        },
        {
          title: '防区编号',
          key: 'zoneNum',
          tooltip: true,
          align: 'center'
        },
        {
          title: '报警类型',
          align: 'center',
          render: (h, params) => {
            if (!params.row.alarmType) return
            return h(
              'span',
              this.alarmTypeName[params.row.alarmType * 1 - 1]
            )
          }
        },
        {
          title: '报警时间',
          key: 'createTime',
          tooltip: true,
          align: 'center'
        }
      ],
      listData: [],
      totalCount: 0,
      alarmTypeName: ['防拆报警', '主机紧急报警', '有线防区报警', '遥控器报警', '无线防区报警', '配件低压', '遥控器低压']
    }
  },
  methods: {
    ...mapActions(['getAlarmList']),
    search () {
      this.searchParam.page = 1
      this.initData()
    },
    change (value) {
      this.searchParam.page = value
      this.initData()
    },
    initData () {
      this.loading = true
      this.getAlarmList(this.searchParam).then(res => {
        this.loading = false
        this.listData = res.data.page.list
        this.totalCount = res.data.page.totalCount
      }).catch(err => {
        this.loading = false
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    },
    timeConfir (res) {
      this.searchParam.createTime = res.filter(item => item !== null && item !== '')
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
