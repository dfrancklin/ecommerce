package br.com.company.ecommerce.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.company.ecommerce.dtos.SaleFromPlatformReport;
import br.com.company.ecommerce.dtos.SaleGeneralReport;
import br.com.company.ecommerce.models.Sale;

public interface SalesRepository extends JpaRepository<Sale, Long> {

	@Query(value = "select to_char(s.created_at, 'YYYY-MM') as month, count(distinct s.id) as count, sum(i.amount * i.sold_price)\\:\\:numeric as sum "
			+ "from sale s "
			+ "  join platform p on s.platform_id = p.id "
			+ "  join sale_item i on i.sale_id = s.id "
			+ "where p.account_id = :account_id "
			+ "  and s.created_at between :start_at and :end_at "
			+ "group by month "
			+ "order by month", nativeQuery = true)
	Page<SaleGeneralReport> findAllSales(@Param("account_id") Long accountId, @Param("start_at") LocalDateTime startAt,
			@Param("end_at") LocalDateTime endAt, Pageable pageable);

	@Query(value = "select to_char(s.created_at, 'YYYY-MM') as month, p.id as platformId, p.name as platformName, count(distinct s.id) as count, sum(i.amount * i.sold_price)\\:\\:numeric as sum "
			+ "from sale s "
			+ "  join platform p on s.platform_id = p.id "
			+ "  join sale_item i on i.sale_id = s.id "
			+ "where p.account_id = :account_id "
			+ "  and s.created_at between :start_at and :end_at "
			+ "group by month, platformId, platformName "
			+ "order by month, platformId, platformName", nativeQuery = true)
	Page<SaleFromPlatformReport> findAllSalesGroupedByPlatform(@Param("account_id") Long accountId,
			@Param("start_at") LocalDateTime startAt, @Param("end_at") LocalDateTime endAt, Pageable pageable);

}
