"""
Gerador de dados fictícios de pacientes para triagem hospitalar.
Requer: pip install faker

Uso:
    python gerar_pacientes.py              # 100 pacientes → pacientes.csv
    python gerar_pacientes.py -n 5000 -o dados/triagem.csv
"""

import csv
import random
import argparse
from faker import Faker

fake = Faker("pt_BR")

HISTORICO_CLINICO = [
    "DOENCA_CARDIACA", "HISTORICO_INFARTO", "HISTORICO_AVC", "HIPERTENSAO",
    "DIABETES", "INSUFICIENCIA_RENAL", "DOENCA_AUTOIMUNE", "DOENCA_PULMONAR",
    "EPILEPSIA", "CANCER", "IMUNOSSUPRESSAO", "ALERGIA_GRAVE",
    "DISTURBIO_COAGULACAO", "TRANSTORNO_PSIQUIATRICO", "HISTORICO_SUICIDIO",
    "USO_DROGAS", "CIRURGIA_RECENTE", "INTERNACAO_RECENTE", "AUTISMO",
    "DEFICIENCIA_INTELECTUAL", "DEFICIENCIA_FISICA", "DEFICIENCIA_VISUAL",
    "DEFICIENCIA_AUDITIVA",
]

def gerar_historico() -> str:
    # ~80% dos pacientes têm ao menos 1 condição
    if random.random() < 0.20:
        return ""
    pesos = [40, 30, 20, 10]  # 1, 2, 3 ou 4 condições
    qtd = random.choices(range(1, 5), weights=pesos)[0]
    return ";".join(random.sample(HISTORICO_CLINICO, qtd))

def gerar_paciente() -> dict:
    sexo = random.choice(["M", "F"])
    return {
        "cpf":              fake.cpf().replace(".", "").replace("-", ""),
        "nome":             fake.name_male() if sexo == "M" else fake.name_female(),
        "sexo":             sexo,
        "dataNascimento":   fake.date_of_birth(minimum_age=0, maximum_age=100).isoformat(),
        "historicoClinico": gerar_historico(),
    }

CAMPOS = ["cpf", "nome", "sexo", "dataNascimento", "historicoClinico"]

def gerar_csv(n: int, caminho: str) -> None:
    with open(caminho, "w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=CAMPOS)
        writer.writeheader()
        for _ in range(n):
            writer.writerow(gerar_paciente())
    print(f"✅  {n} pacientes gerados em '{caminho}'")

def main() -> None:
    parser = argparse.ArgumentParser(description="Gerador de pacientes fictícios.")
    parser.add_argument("-n", "--quantidade", type=int, default=100)
    parser.add_argument("-o", "--saida", default="pacientes.csv")
    args = parser.parse_args()
    if args.quantidade <= 0:
        parser.error("A quantidade deve ser maior que zero.")
    gerar_csv(args.quantidade, args.saida)

if __name__ == "__main__":
    main()