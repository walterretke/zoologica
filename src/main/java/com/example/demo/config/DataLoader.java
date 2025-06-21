package com.example.demo.config;

import com.example.demo.models.AnimalTemplate;
import com.example.demo.models.CageType;
import com.example.demo.repositories.AnimalTemplateRepository;
import com.example.demo.repositories.CageTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CageTypeRepository cageTypeRepository;
    private final AnimalTemplateRepository animalTemplateRepository;

    @Override
    public void run(String... args) throws Exception {
        if (cageTypeRepository.count() == 0) {
            createCageTypes();
            createAnimalTemplates();
        }
    }

    private void createCageTypes() {
        // Nível 1 - ELEPHANT (Mais fácil)
        CageType elephant = new CageType(
                "ELEPHANT",
                "Elefante",
                1,
                100,
                "Soma e Subtração",
                "Jaula para elefantes. Problemas básicos de soma e subtração com números até 20."
        );
        cageTypeRepository.save(elephant);

        // Nível 2 - LION
        CageType lion = new CageType(
                "LION",
                "Leão",
                2,
                200,
                "Multiplicação Básica",
                "Jaula para leões. Introdução à multiplicação com números até 10."
        );
        cageTypeRepository.save(lion);

        // Nível 3 - GIRAFFE
        CageType giraffe = new CageType(
                "GIRAFFE",
                "Girafa",
                3,
                350,
                "Divisão e Frações Simples",
                "Jaula para girafas. Divisão exata e introdução a frações simples."
        );
        cageTypeRepository.save(giraffe);

        // Nível 4 - MONKEY
        CageType monkey = new CageType(
                "MONKEY",
                "Macaco",
                4,
                500,
                "Expressões e Potências",
                "Jaula para macacos. Expressões numéricas e potências simples."
        );
        cageTypeRepository.save(monkey);

        // Nível 5 - ZEBRA (Mais difícil)
        CageType zebra = new CageType(
                "ZEBRA",
                "Zebra",
                5,
                750,
                "Equações e Geometria",
                "Jaula para zebras. Equações do 1º grau e cálculos geométricos básicos."
        );
        cageTypeRepository.save(zebra);

        System.out.println("✅ Cage types created successfully!");
    }

    private void createAnimalTemplates() {
        // Buscar os tipos de jaula criados
        CageType elephant = cageTypeRepository.findByName("ELEPHANT").orElseThrow();
        CageType lion = cageTypeRepository.findByName("LION").orElseThrow();
        CageType giraffe = cageTypeRepository.findByName("GIRAFFE").orElseThrow();
        CageType monkey = cageTypeRepository.findByName("MONKEY").orElseThrow();
        CageType zebra = cageTypeRepository.findByName("ZEBRA").orElseThrow();

        // Animais para ELEPHANT (Nível 1)
        animalTemplateRepository.save(new AnimalTemplate("Dumbo", 50, "Um elefante gentil e inteligente", elephant));
        animalTemplateRepository.save(new AnimalTemplate("Ellie", 55, "Uma elefante corajosa", elephant));
        animalTemplateRepository.save(new AnimalTemplate("Jumbo", 60, "O maior elefante do zoológico", elephant));

        // Animais para LION (Nível 2)
        animalTemplateRepository.save(new AnimalTemplate("Simba", 80, "O rei da selva", lion));
        animalTemplateRepository.save(new AnimalTemplate("Mufasa", 90, "Um leão sábio e poderoso", lion));
        animalTemplateRepository.save(new AnimalTemplate("Nala", 85, "Uma leoa ágil e esperta", lion));

        // Animais para GIRAFFE (Nível 3)
        animalTemplateRepository.save(new AnimalTemplate("Geoffrey", 120, "Uma girafa alta e elegante", giraffe));
        animalTemplateRepository.save(new AnimalTemplate("Melman", 125, "Uma girafa preocupada mas carinhosa", giraffe));
        animalTemplateRepository.save(new AnimalTemplate("Stretch", 130, "A girafa mais alta do zoológico", giraffe));

        // Animais para MONKEY (Nível 4)
        animalTemplateRepository.save(new AnimalTemplate("King Kong", 150, "Um macaco forte e protetor", monkey));
        animalTemplateRepository.save(new AnimalTemplate("Bananas", 140, "Um macaco travesso que adora bananas", monkey));
        animalTemplateRepository.save(new AnimalTemplate("George", 145, "Um macaco curioso e aventureiro", monkey));

        // Animais para ZEBRA (Nível 5)
        animalTemplateRepository.save(new AnimalTemplate("Marty", 200, "Uma zebra enérgica e divertida", zebra));
        animalTemplateRepository.save(new AnimalTemplate("Stripe", 210, "Uma zebra com listras perfeitas", zebra));
        animalTemplateRepository.save(new AnimalTemplate("Zigzag", 205, "Uma zebra rápida como o vento", zebra));

        System.out.println("✅ Animal templates created successfully!");
    }
}