import sys
import io
from paddlenlp import Taskflow
import os
from openpyxl import Workbook
sys.stdout=io.TextIOWrapper(sys.stdout.buffer,encoding='utf8')
# zhuti="竞赛名称"
# gx1="主办方"
# gx2="承办方"
# gx3="已举办次数"
# text="2022语言与智能技术竞赛由中国中文信息学会和中国计算机学会联合主办，百度公司、中国中文信息学会评测工作委员会和中国计算机学会自然语言处理专委会承办，已连续举办4届，成为全球最热门的中文NLP赛事之一。"
zhuti=os.environ.get("zhuti")
gx1=os.environ.get("gx1")
gx2=os.environ.get("gx2")
gx3=os.environ.get("gx3")
text=os.environ.get("text")

schema = {zhuti: [gx1, gx2, gx3]}
ie = Taskflow('information_extraction', schema=schema)
ie.set_schema(schema)
data = ie(text)

text_content = data[0][zhuti][0]['text']
# 抽取每一个relations键对应的第一个text键的内容
relations = data[0][zhuti][0]['relations']
for relation, entities in relations.items():
    first_entity_text = entities[0]['text']
    print(f"{text_content}:{relation}:{first_entity_text}")
# 创建一个新的 Excel 工作簿
wb = Workbook()
# 选择活动工作表
ws = wb.active

# 定义表头
ws.append(["zhiti", "Relation", "keti"])

for relation, entities in relations.items():
    first_entity_text = entities[0]['text']
    # 将结果写入 Excel 表格
    ws.append([text_content, relation, first_entity_text])

# 保存 Excel 文件
wb.save("output.xlsx")
